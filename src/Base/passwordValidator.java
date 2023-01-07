
package Base;

/**
 *
 * @author root
 */
public class passwordValidator {

    public static boolean validateePassword(final String password) {
        int num;
        return (password.matches(".*[0-9]{1,}.*")
                && password.matches(".*[A-Z]{1,}.*")
                && password.matches(".*[!@#$%^&*()_+><?/':;~]{1,}.*")
                && password.matches(".*[@#$%]{1,}.*")
                && password.length() >= 6
                && password.length() <= 20);

    }

}
