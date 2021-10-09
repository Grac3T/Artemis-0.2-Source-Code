/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.encryption;

import java.io.PrintStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class IpTransformer {
    public static String serialize(String s, String key) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, skeySpec);
            byte[] encrypted = cipher.doFinal(Base64.getDecoder().decode(s));
            return Arrays.toString(Base64.getEncoder().encode(encrypted));
        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw e;
        }
    }

    public static String deserialize(String s, String key) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, skeySpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(s));
            return Arrays.toString(Base64.getEncoder().encode(decrypted));
        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw e;
        }
    }
}

