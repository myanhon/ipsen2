package Controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Deze classe wordt gebruikt om de wachtwoorden te kunnen hashen binnen de applicatie.
 * Created by murtazaaydogdu on 25-10-16.
 * @author Murtaza Aydogdu
 * @version 1.0 Nov, 2016
 */
public class PasswordEncryption {

    public static String GenerateHash(String input) {
        MessageDigest objSHA = null;
        try {
            objSHA = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] bytSHA = objSHA.digest(input.getBytes());
        BigInteger intNumber = new BigInteger(1, bytSHA);
        String strHashCode = intNumber.toString(16);

        while (strHashCode.length() < 128) {
            strHashCode = "0" + strHashCode;
        }
        return strHashCode;
    }

}
