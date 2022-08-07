package com.doxa.oauth2.serviceImpl;

import com.doxa.oauth2.services.IMultiFactorService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.awt.image.BufferedImage;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

@Service
public class MultiFactorService implements IMultiFactorService {
    @Override
    public String generateQRCode(String secret) {
        return null;
    }

    @Override
    public BufferedImage createQRCode(String email, String secret) throws WriterException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        String barCodeData = getGoogleAuthenticatorBarCode(email, secret);
        int height = 200;
        int width = 200;
        BitMatrix matrix =
                barcodeWriter.encode(barCodeData, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(matrix);
    }

    @Override
    public String getTOTPCode(String secret) {
        String hexKey = convertSecret(secret);
        return TOTP.getOTP(hexKey);
    }

    private String getGoogleAuthenticatorBarCode(String email, String secret) {
        //Account is the user id in system. Usually it’s user’s email or username
        //Issuer is a company or organization name and is also used for labelling purposes.
        String issuer = "Doxa Holdings";
        return "otpauth://totp/"
                + URLEncoder.encode(issuer + ":" + email, StandardCharsets.UTF_8).replace("+", "%20")
                + "?secret=" + URLEncoder.encode(secret, StandardCharsets.UTF_8).replace("+", "%20")
                + "&issuer=" + URLEncoder.encode(issuer, StandardCharsets.UTF_8).replace("+", "%20");
    }
    
    public String convertSecret(String secret) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secret);
        String hexKey = Hex.encodeHexString(bytes);
        return hexKey;
    }

    public boolean validate(long step, String key, String otp) {
    	 String convertedSecret = convertSecret(key);
        return getOTP(step, convertedSecret).equals(otp) || getOTP(step - 1L, convertedSecret).equals(otp);
    }

    public static String getOTP(String key) {
        return getOTP(getStep(), key);
    }

    public String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        String secret = base32.encodeToString(bytes);
        return secret;
    }

    public boolean validate(String key, String otp) {
        return validate(getStep(), key, otp);
    }

    public static long getStep() {
        return System.currentTimeMillis() / 30000L;
    }

    private static String getOTP(long step, String key) {
        String steps = Long.toHexString(step).toUpperCase();
        while (steps.length() < 16) {
			steps = "0" + steps;
		}
        byte[] msg = hexStr2Bytes(steps);
        byte[] k = hexStr2Bytes(key);
        byte[] hash = hmac_sha1(k, msg);
        final int offset = hash[hash.length - 1] & 0xf;
        final int binary = ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16) | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);
		final int otp = binary % 1000000;
        String result = Integer.toString(otp);
		while (result.length() < 6) {
			result = "0" + result;
		}
        return result;
    }

    private static byte[] hexStr2Bytes(String hex) {
        byte[] bArray = (new BigInteger("10" + hex, 16)).toByteArray();
        byte[] ret = new byte[bArray.length - 1];
        System.arraycopy(bArray, 1, ret, 0, ret.length);
        return ret;
    }

    private static byte[] hmac_sha1(byte[] keyBytes, byte[] text) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA1");
            SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
            hmac.init(macKey);
            return hmac.doFinal(text);
        } catch (GeneralSecurityException var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }
}
