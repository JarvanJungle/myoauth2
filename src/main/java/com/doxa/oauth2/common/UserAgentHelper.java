package com.doxa.oauth2.common;

import com.google.common.base.Strings;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

@Service
@Slf4j
public class UserAgentHelper {

//    private DatabaseReader databaseReader;
//
//    public UserAgentHelper(@Qualifier("GeoIPCity") DatabaseReader databaseReader) {
//        this.databaseReader = databaseReader;
//    }
    private String exactIP(HttpServletRequest request) {
        String clientIp;
        String clientXForwardedForIp = request.getHeader("x-forwarded-for");
        if (clientXForwardedForIp != null) {
            clientIp = parseXForwardedHeader(clientXForwardedForIp);
        } else {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

    private String parseXForwardedHeader(String header) {
        return header.split(" *, *")[0];
    }

//    public String getLocation(HttpServletRequest request) throws IOException, GeoIp2Exception {
//        String ip = exactIP(request);
//        return getIpLocation(ip);
//    }

    public String getIpAddress(HttpServletRequest request) {
        return exactIP(request);
    }

//    private String getIpLocation(String ip){
//
//        String location ="UNKNOWN";
//
//        try {
//            InetAddress ipAddress = InetAddress.getByName(ip);
//
//            CityResponse cityResponse = databaseReader.city(ipAddress);
//            if (Objects.nonNull(cityResponse) &&
//                    Objects.nonNull(cityResponse.getCity()) &&
//                    !Strings.isNullOrEmpty(cityResponse.getCity().getName())) {
//
//                location = cityResponse.getCity().getName();
//            }
//        } catch (IOException | GeoIp2Exception ex) {
//            log.error(ex.getMessage());
//        }
//
//        return location;
//    }

    private String getLocationInet(String ip) {
        String location = "UNKNOWN";
        try {
            InetAddress inetAddress =  InetAddress.getByName(ip);
            location = inetAddress.getHostName();
        } catch (UnknownHostException ex) {
            log.error(ex.getMessage());
        }
        return location;
    }

}
