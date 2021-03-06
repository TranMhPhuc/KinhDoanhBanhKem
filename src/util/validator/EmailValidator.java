package util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    public enum EmailValidateResult {
        EMPTY,
        INVALLID,
        NO_ERROR,
    }

    private static final Pattern EMAIL_VALID_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    private EmailValidator() {
    }

    public static boolean isEmailEmpty(String email) {
        return email.trim().isEmpty();
    }

    public static boolean isEmailVallid(String email) {
        Matcher matcher = EMAIL_VALID_PATTERN.matcher(email);
        return matcher.matches();
    }

    public static EmailValidateResult validate(String email) {
        String input = email.trim();

        if (email.isEmpty()) {
            return EmailValidateResult.EMPTY;
        } else {
            Matcher matcher = EMAIL_VALID_PATTERN.matcher(email);
            if (!matcher.matches()) {
                return EmailValidateResult.INVALLID;
            }
        }
        
        return EmailValidateResult.NO_ERROR;
    }

}
