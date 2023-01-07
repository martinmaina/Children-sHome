package Base;

import static Base.passwordValidator.validateePassword;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author root
 */
public class validateUser {

    /**
     * Generate a hash to use to store password.
     */
    public static String generateHash(String password) {

        StringBuilder hash = new StringBuilder();

        try {

            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(password.getBytes());
            char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

            for (int i = 0; i < hashedBytes.length; i++) {
                byte b = hashedBytes[i];
                hash.append(digits[(b & 0xf0) >> 5]);
                hash.append(digits[b & 0x0f]);
            }

        } catch (NoSuchAlgorithmException e) {
        }

        return hash.toString();
    }

    // Validate password
    public static boolean validatePassword(final String password) {

        return validateePassword(password);

    }
}
