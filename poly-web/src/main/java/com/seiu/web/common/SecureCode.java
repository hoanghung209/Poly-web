/*
 * Created on Mar 7, 2012
 *
 * Copyright (C) 2003 - 2012 by Vega. All rights reserved
 */
package com.seiu.web.common;

import java.math.BigInteger;
//import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;


/**
 * @author hanh
 *
 */
public class SecureCode {

    public static Random random = null;
    private static Logger log = Logger.getLogger("SecureCode.class");

    public static void createSecureRandom() {
        try {
            if (random == null) {
                random = SecureRandom.getInstance("SHA1PRNG");
            }
        } catch (NoSuchAlgorithmException ex) {
            log.error("Error NoSuchAlgorithmException while creating secure random", ex);
        }
    }

  
    public static String generateSecureCodeExt(String... params) {
        String encodeString = "";
        for (int i = 0; i < params.length; i++) {
            encodeString = encodeString.equals("") ? params[i].trim() : encodeString + params[i].trim();
        }
        return md5(encodeString);
    }

    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.trim();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

//    public static String hmacSHA1(String data, String key) {
//        String resultStr = "";
//        if (key == null) {
//            key = "";
//        }
//        SecretKeySpec keySpec = new SecretKeySpec(
//                key.getBytes(),
//                "HmacSHA1");
//        Mac mac;
//        try {
//            mac = Mac.getInstance("HmacSHA1");
//            mac.init(keySpec);
//            byte[] result = mac.doFinal(data.getBytes());
//            resultStr = Base64.encodeBase64String(result);
//        } catch (NoSuchAlgorithmException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return resultStr.trim();
//    }
    
}
