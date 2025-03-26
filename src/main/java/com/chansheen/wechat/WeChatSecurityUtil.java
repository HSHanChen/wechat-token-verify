package com.chansheen.wechat;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class WeChatSecurityUtil {
    private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String CHARSET = "UTF-8";

    public static String decryptMessage(String encryptedMsg, String encodingAesKey) throws Exception {
        byte[] aesKey = Base64.getDecoder().decode(encodingAesKey + "=");
        byte[] encryptedData = Base64.getDecoder().decode(encryptedMsg);

        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(aesKey, 0, 16);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] decryptedData = cipher.doFinal(encryptedData);
        return new String(decryptedData, CHARSET);
    }
}
