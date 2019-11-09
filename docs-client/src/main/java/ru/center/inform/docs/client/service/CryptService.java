package ru.center.inform.docs.client.service;

public interface CryptService {
    byte[] encryptData(byte[] data);
    byte[] decryptData(byte[] encryptedData);
    byte[] signData(byte[] data);
    boolean verifySignedData(byte[] signedData);
}
