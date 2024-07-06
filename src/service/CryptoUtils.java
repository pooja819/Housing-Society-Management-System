package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sdm.hsmp.controller.CryptoException;
 

public class CryptoUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
 
    public static void encrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }
 
    public static void decrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }
 
    private static void doCrypto(int cipherMode, String key, File inputFile ,File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);
             
            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
             
            byte[] outputBytes = cipher.doFinal(inputBytes);
             
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
             
            inputStream.close();
            outputStream.close();
             
        } catch ( Exception ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }
}
