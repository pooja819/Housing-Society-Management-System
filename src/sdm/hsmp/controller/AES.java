package sdm.hsmp.controller;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
 
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;
 
public class AES {
	//final String secretKey = "ssshhhhhhhhhhh!!!!";
    private static SecretKeySpec secretKey;
    private static byte[] key;
 
    public static void main(String args[])
    {
    	System.out.println(AES.encrypt("admin"));
    	System.out.println(AES.encrypt("guard"));
    	System.out.println(AES.decrypt("YWRtaW4="));
    	System.out.println(AES.decrypt("Z3VhcmQ="));
    	
    
    }
    
    public static void setKey(String myKey) 
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 
    public static String encrypt(String strToEncrypt) 
    {
        try
        {
            setKey("secretKey");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            String encoding = new String(
            		 org.apache.commons.codec.binary.Base64.encodeBase64   
            		    (org.apache.commons.codec.binary.StringUtils.getBytesUtf8(strToEncrypt)));
            return  encoding;
        } 
        catch (Exception e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
 
    public static String decrypt(String strToDecrypt) 
    {
        try
        {
            setKey("secretKey");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            String decoding = new String(
           		 org.apache.commons.codec.binary.Base64.decodeBase64   
           		    (org.apache.commons.codec.binary.StringUtils.getBytesUtf8(strToDecrypt)));
           
            
            return decoding;
        } 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}