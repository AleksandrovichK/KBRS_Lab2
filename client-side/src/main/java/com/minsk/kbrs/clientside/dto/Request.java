package com.minsk.kbrs.clientside.dto;

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

    public void setPublicKeyRSA(String publicKeyRSA) {
        this.publicKeyRSA = publicKeyRSA;
    }
}