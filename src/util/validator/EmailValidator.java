package util.validator;

import static control.employee.EmployeeController.EMAIL_PATTERN;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final Pattern EMAIL_VALID_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    private EmailValidator() {
    }

    public static boolean isEmailEmpty(String email) {
        return email.trim().isEmpty();
    }

    public static boolean isEmailVallid(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public static boolean isAvailable(String email) {
        return false;
    }
}
