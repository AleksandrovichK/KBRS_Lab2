package com.minsk.kbrs.serverside.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import sun.security.rsa.RSAPublicKeyImpl;

/**
 * @author AleksandrovichK
 */
public class CryptoUtils {
    public static String generateTemporaryKey() {
        return new RandomString(16, ThreadLocalRandom.current()).nextString();
    }

    public static String getFileData(String filename) {
        try (InputStream textStream = new ClassPathResource("secret-files/" + filename).getInputStream()) {
            return IOUtils.toString(textStream, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encryptAES(String data, String temporaryKey) {
        if (data != null) {
            try {
                Key aesKey = new SecretKeySpec(temporaryKey.getBytes(), "AES");
                Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
                IvParameterSpec ivParameterSpec = new IvParameterSpec(aesKey.getEncoded());
                cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivParameterSpec);

                byte[] encrypted = cipher.doFinal(data.getBytes());
                return Base64.getEncoder().encodeToString(encrypted);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
                e.printStackTrace();
                return null;
            }
        } else
            return null;
    }

    public static String encryptRSA(String data, byte[] key) {
        try {
            Cipher c = Cipher.getInstance("RSA");
            PublicKey publicKey = new RSAPublicKeyImpl(key);
            c.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypted = c.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
