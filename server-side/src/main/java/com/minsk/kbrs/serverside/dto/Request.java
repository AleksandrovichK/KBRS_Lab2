package com.minsk.kbrs.serverside.dto;

import java.util.Base64;

public class Request {
    private String fileName;
    private String publicKeyRSA;

    public Request(String fileName, String publicKeyRSA) {
        this.fileName = fileName;
        this.publicKeyRSA = publicKeyRSA;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPublicKeyRSA() {
        return publicKeyRSA;
    }

    public byte[] getDecodedRSAKey(){
        return Base64.getDecoder().decode(publicKeyRSA);
    }

    public void setPublicKeyRSA(String publicKeyRSA) {
        this.publicKeyRSA = publicKeyRSA;
    }
}