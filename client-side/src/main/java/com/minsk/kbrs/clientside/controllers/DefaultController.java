package com.minsk.kbrs.clientside.controllers;

import java.security.KeyPair;
import java.security.PrivateKey;

import com.minsk.kbrs.clientside.dto.Response;
import com.minsk.kbrs.clientside.utils.CryptoUtils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KotsyubaT
 */
@RestController
public class DefaultController {

    /**
     * algorithm
     * - client -
     * proceed()
     * - generates private and public key
     * - sends to server filename and public key
     * - decrypts with private key temporary key and decrypts text file
     */
    @RequestMapping(method = RequestMethod.GET, value = "/start")
    public String proceed(@RequestParam String filename) {
        KeyPair keyPair = CryptoUtils.getKeyPair();
        String publicKey = Base64.encodeBase64String(keyPair.getPublic().getEncoded());
        PrivateKey privateKey = keyPair.getPrivate();

        System.out.println("\n\nRequest is sent.");
        Response encryptedFile = CryptoUtils.getEncryptedFile(publicKey, filename);
        String decryptedTemporaryKey = CryptoUtils.decryptRSA(encryptedFile.getEncryptedTemporaryKey(), privateKey);
        String data = CryptoUtils.decryptAES(encryptedFile.getEncryptedData(), decryptedTemporaryKey);

        System.out.println("Response is accepted.");
        System.out.println("Decrypted temporary key: " + decryptedTemporaryKey);
        System.out.println("Open text: " + data);
        return "Open text: " + data + ",\nDecrypted temporary key: " + decryptedTemporaryKey;
    }
}

