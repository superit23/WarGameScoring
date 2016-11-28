package bb.rackmesa.wargamescoring;

import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

/**
 * Created by Dan on 9/8/2016.
 */
public class CryptoFunctions {

    static SecureRandom rand = new SecureRandom();
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(CryptoFunctions.class);

    public static String convertByteToHex(byte data[])
    {
        StringBuffer hexData = new StringBuffer();
        for (int byteIndex = 0; byteIndex < data.length; byteIndex++)
            hexData.append(Integer.toString((data[byteIndex] & 0xff) + 0x100, 16).substring(1));

        return hexData.toString();
    }

    public static String hashSHA512(String input)
    {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update(input.getBytes());
            byte[] output = digest.digest();
            return convertByteToHex(output);
        }
        catch (NoSuchAlgorithmException ex)
        {
            //System.err.println(ex.getMessage());
            logger.error(ex.getMessage());
            return "ERROR No such algorithm";
        }
    }


    public static byte[] generateSalt(int numChars)
    {
        byte[] salt = new byte[numChars];
        rand.nextBytes(salt);

        return salt;
    }


    public static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        }
        catch (NoSuchAlgorithmException ex)
        {
            logger.error(ex.getMessage());
            return null;
        }
        catch (InvalidKeySpecException ex)
        {
            logger.error(ex.getMessage());
            return null;
        }

    }


    public static String generateUUID()
    {
        return UUID.randomUUID().toString();
    }

    public static boolean slowEquals(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    public static byte[] combineArrays(byte[] first, byte[] second)
    {
        byte[] combined = new byte[first.length + second.length];

        System.arraycopy(first, 0, combined, 0, first.length);
        System.arraycopy(second, 0, combined, first.length, second.length);

        return combined;
    }


}