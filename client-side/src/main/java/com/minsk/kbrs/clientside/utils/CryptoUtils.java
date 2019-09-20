package com.minsk.kbrs.clientside.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.minsk.kbrs.clientside.dto.Request;
import com.minsk.kbrs.clientside.dto.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author AleksandrovichK
 */
public class CryptoUtils {

    public static String decryptAES(String data, String key) {
        if (data != null) {
            try {
                Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
                Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
                IvParameterSpec ivParameterSpec = new IvParameterSpec(aesKey.getEncoded());
                cipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);

                byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(data));
                return new String(decrypted);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
                e.printStackTrace();
                return null;
            }
        } else
            return null;
    }

    public static Response getEncryptedFile(String publicKey, String filename) {
        String url = "http://localhost:8080/encrypted-file";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Response> responseEntityPerson = restTemplate.postForEntity(url, new Request(filename, publicKey), Response.class);
        return responseEntityPerson.getBody();
    }

    public static KeyPair getKeyPair() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            return kpg.generateKeyPair();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptRSA(String data, PrivateKey privateKey) {
        if (data != null && privateKey != null) {
            try {
                Cipher c = Cipher.getInstance("RSA");
                c.init(Cipher.DECRYPT_MODE, privateKey);
                byte[] decrypted = c.doFinal(Base64.getDecoder().decode(data));
                return new String(decrypted);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
                return null;
            }
        } else
            return null;
    }
}
