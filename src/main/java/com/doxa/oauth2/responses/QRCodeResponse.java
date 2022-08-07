package com.doxa.oauth2.responses;

import lombok.Data;

@Data
public class QRCodeResponse {

    private String base64;
    private String secretKey;

}
