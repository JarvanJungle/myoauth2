package com.doxa.oauth2.DTO.privateAPI;

import lombok.Data;

/**
 * @author vuducnoi
 * Enhancement for private api to get company information
 */
@Data
public class Company {
    private String companyCode;

    private String companyName;

    private String countryCode;

    private String logo;

    private String contactPersonEmail;

    private String contactPersonName;

    private String contactPersonWorkNumber;

    private String gstRegNo;

    private String uen;

    private String countryOfOrigin;

    private String uuid;

    private boolean connected;
}
