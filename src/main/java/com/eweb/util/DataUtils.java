package com.eweb.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class DataUtils {
	public static String getHash(
            String data,
            String secret
    ) throws Exception {

        Mac sha256_HMAC =
                Mac.getInstance("HmacSHA256");

        SecretKeySpec secret_key =
                new SecretKeySpec(
                        secret.getBytes(),
                        "HmacSHA256"
                );

        sha256_HMAC.init(secret_key);

        byte[] hash =
                sha256_HMAC.doFinal(
                        data.getBytes()
                );

        StringBuilder sb =
                new StringBuilder();

        for(byte b : hash) {
            sb.append(
                    String.format("%02x", b)
            );
        }

        return sb.toString();
    }
}
