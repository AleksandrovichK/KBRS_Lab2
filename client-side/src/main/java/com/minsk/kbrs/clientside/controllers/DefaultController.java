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
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

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

            System.out.println(ANSI_RED + "\n\nRequest is sent." + ANSI_RESET);
            Response encryptedFile = CryptoUtils.getEncryptedFile(publicKey, filename);
            String decryptedTemporaryKey = CryptoUtils.decryptRSA(encryptedFile.getEncryptedTemporaryKey(), privateKey);
            String data = CryptoUtils.decryptAES(encryptedFile.getEncryptedData(), decryptedTemporaryKey);

            System.out.println(ANSI_RED + "Response is accepted." + ANSI_RESET);
            System.out.print("\033[1mDecrypted temporary key: \033[0m");
            System.out.print(ANSI_GREEN + decryptedTemporaryKey + ANSI_RESET);
            System.out.print("\n\033[1mOpen text: \033[0m");
            System.out.print(ANSI_GREEN + data + ANSI_RESET);
            return "Open text: " + data + ",\nDecrypted temporary key: " + decryptedTemporaryKey;
        }
        return "KeyPair were generated with mistake";
    }
}

