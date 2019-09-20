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
 * @author AleksandrovichK
 */
@RestController
public class DefaultController {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_BLUE = "\u001B[34m";


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
        if (keyPair != null) {
            String publicKey = Base64.encodeBase64String(keyPair.getPublic().getEncoded());
            PrivateKey privateKey = keyPair.getPrivate();

            System.out.println(ANSI_RED + "\n\n\n Request is sent." + ANSI_RESET);
            Response encryptedFile = CryptoUtils.getEncryptedFile(publicKey, filename);
            String decryptedTemporaryKey = CryptoUtils.decryptRSA(encryptedFile.getEncryptedTemporaryKey(), privateKey);
            String data = CryptoUtils.decryptAES(encryptedFile.getEncryptedData(), decryptedTemporaryKey);

            System.out.println(ANSI_RED + " Response is accepted." + ANSI_RESET);
            System.out.print("\033[1m Accepted encrypted temporary key: \033[0m");
            System.out.print(ANSI_PURPLE + encryptedFile.getEncryptedTemporaryKey() + ANSI_RESET);
            System.out.print("\n\033[1m Accepted encrypted text: \033[0m");
            System.out.print(ANSI_CYAN + encryptedFile.getEncryptedData() + ANSI_RESET);

            System.out.print(ANSI_BLUE + "\n-------------------------------------------" + ANSI_RESET);

            System.out.print("\n\033[1m Decrypted temporary key: \033[0m");
            System.out.print(ANSI_GREEN + decryptedTemporaryKey + ANSI_RESET);
            System.out.print("\n\033[1m Open text: \033[0m");
            System.out.print(ANSI_GREEN + data + ANSI_RESET);
            return "Open text: " + data + ",\nDecrypted temporary key: " + decryptedTemporaryKey;
        }
        return "KeyPair were generated with mistake";
    }
}

