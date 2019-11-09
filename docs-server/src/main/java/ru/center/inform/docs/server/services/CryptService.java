package ru.center.inform.docs.server.services;

public interface CryptService {
    byte[] encryptData(byte[] data);
    byte[] decryptData(byte[] encryptedData);
    byte[] signData(byte[] data);
    boolean verifySignedData(byte[] signedData);
}
