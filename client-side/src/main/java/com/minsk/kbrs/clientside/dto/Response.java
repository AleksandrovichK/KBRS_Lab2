package com.minsk.kbrs.clientside.dto;

/**
 * @author AleksandrovichK
 */
public class Response {
    private String encryptedData;
    private String encryptedTemporaryKey;

    public Response() {
    }

    public Response(String encryptedData, String encryptedTemporaryKey) {
        this.encryptedData = encryptedData;
        this.encryptedTemporaryKey = encryptedTemporaryKey;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getEncryptedTemporaryKey() {
        return encryptedTemporaryKey;
    }

    public void setEncryptedTemporaryKey(String encryptedTemporaryKey) {
        this.encryptedTemporaryKey = encryptedTemporaryKey;
    }

    @Override
    public String toString() {
        return "Response{" +
                "encryptedData='" + encryptedData + '\'' +
                ", encryptedTemporaryKey='" + encryptedTemporaryKey + '\'' +
                '}';
    }
}
