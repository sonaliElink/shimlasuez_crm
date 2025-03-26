package elink.suezShimla.water.crm.AesAlgorithm;

import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AesAlgorithm {

    /* public byte[] encrypt(byte[] plaintext, SecretKey key, byte[] IV) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] cipherText = cipher.doFinal(plaintext);
        return cipherText;
    }

    public String decrypt(byte[] cipherText, SecretKey key, byte[] IV) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decryptedText = cipher.doFinal(cipherText);
            return new String(decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    } */

    public String decrypt (String outputString) throws Exception{
        SecretKeySpec key =generateKey ("TestPassword");
        Cipher c = Cipher.getInstance("AES");
        c.init (Cipher.DECRYPT_MODE, key);
        byte[] decodedValue= Base64.decode (outputString, Base64.DEFAULT);
        byte [] decValue= c.doFinal (decodedValue) ;
        String decryptedValue= new String (decValue);
        return decryptedValue;
    }

    public String encrypt (String inputString) throws Exception {
        SecretKeySpec key =generateKey ("TestPassword") ;
        Cipher c = Cipher.getInstance("AES");
        c.init (Cipher.ENCRYPT_MODE, key);
        byte[] encVal=c.doFinal (inputString.getBytes() ) ;
        String encryptedValue =Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }
    
    public SecretKeySpec generateKey (String password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance ("SHA-256");
        byte [] bytes =password.getBytes ("UTF-8");
        digest.update (bytes, 0, bytes . length);
        byte [] key =digest.digest ();
        SecretKeySpec secretKeyspec= new SecretKeySpec (key, "AES");
        return secretKeyspec;
    }

    public static String encryptUrl(String toEncrypt, boolean useHashing, String key) {
        try {
            byte[] keyBytes;
            byte[] toEncryptBytes = toEncrypt.getBytes("UTF-8");

            if (useHashing) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                keyBytes = md.digest(key.getBytes("UTF-8"));
            } else {
                keyBytes = key.getBytes("UTF-8");
            }

            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] encryptedBytes = cipher.doFinal(toEncryptBytes);

            return android.util.Base64.encodeToString(encryptedBytes, android.util.Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
