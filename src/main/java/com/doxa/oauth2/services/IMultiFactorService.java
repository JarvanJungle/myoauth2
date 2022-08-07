package com.doxa.oauth2.services;

import com.google.zxing.WriterException;

import java.awt.image.BufferedImage;

public interface IMultiFactorService {
    public String generateQRCode(String secret);

    public BufferedImage createQRCode(String email, String secret) throws WriterException;

    public String getTOTPCode(String secret);

}
