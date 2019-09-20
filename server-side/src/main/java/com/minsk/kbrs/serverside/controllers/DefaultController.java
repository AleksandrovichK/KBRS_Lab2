package com.minsk.kbrs.serverside.controllers;

import com.minsk.kbrs.serverside.dto.Request;
import com.minsk.kbrs.serverside.dto.Response;
import com.minsk.kbrs.serverside.utils.CryptoUtils;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * algorithm:
     * - server -
     * getEncryptedFile(byte[] publicKeyRSA, String fileName): byte[]
     * - generates it's own temporary key
     * - encrypts file %fileName% with temporary key
     * - encrypts temporary key with %publicKeyRSA% and appends it to response
     **/
    @RequestMapping(method = RequestMethod.POST, value = "/encrypted-file", consumes = {"application/json;charset=UTF-8"})
    public Response getEncryptedFile(@RequestBody Request request) {
        String temporaryKey = CryptoUtils.generateTemporaryKey();
        byte[] publicKeyRSA = request.getDecodedRSAKey();
        String data = CryptoUtils.getFileData(request.getFileName());

        String encryptedData = CryptoUtils.encryptAES(data, temporaryKey);
        String encryptedTemporaryKey = CryptoUtils.encryptRSA(temporaryKey, publicKeyRSA);

        System.out.println(ANSI_RED + "\n\n\n Request is accepted." + ANSI_RESET);
        System.out.print("\033[1m Generated temporary key before encryption: \033[0m");
        System.out.print(ANSI_GREEN + temporaryKey + ANSI_RESET);
        System.out.print("\n\033[1m Open text before encryption: \033[0m");
        System.out.println(ANSI_GREEN + data + ANSI_RESET);
        System.out.print(ANSI_BLUE + "-------------------------------------------" + ANSI_RESET);
        System.out.print("\n\033[1m Generated temporary key after encryption: \033[0m");
        System.out.print(ANSI_PURPLE + encryptedTemporaryKey + ANSI_RESET);
        System.out.print("\n\033[1m Open text after encryption: \033[0m");
        System.out.print(ANSI_CYAN + encryptedData + ANSI_RESET);
        return new Response(encryptedData, encryptedTemporaryKey);
    }
}

