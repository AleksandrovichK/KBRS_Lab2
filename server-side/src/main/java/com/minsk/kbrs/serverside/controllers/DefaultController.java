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

        System.out.println("\n\nRequest is accepted.");
        System.out.println("Generated temporary key before encryption: " + temporaryKey);
        System.out.println("Open text before encryption: " + data);
        System.out.println("-------------------------------------------");
        System.out.println("Generated temporary key after encryption: " + encryptedTemporaryKey);
        System.out.println("Open text after encryption: " + encryptedData);
        return new Response(encryptedData, encryptedTemporaryKey);
    }
}

