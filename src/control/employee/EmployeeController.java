package control.employee;



import java.util.regex.Matcher;
import java.util.regex.Pattern;
import view.MessageShowing;


public class EmployeeController {

    public enum EmailCheckCode {
        EMPTY,
        INVALLID,
        VALID,
    }

    public enum PassWordCheckCode {
        EMPTY,
        INCORRECT,
        CORRECT,
    }

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    private MessageShowing ui;

    public void requestLogin(String email, String password) {

    }

    /**
     * Validate email method.
     *
     * @param email
     * @return 0 - email is empty
     */
    private static EmailCheckCode validateEmail(String email) {
        if (email.isEmpty()) {
            return EmailCheckCode.EMPTY;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            return EmailCheckCode.INVALLID;
        }

        return EmailCheckCode.VALID;
    }

    private static PassWordCheckCode validatePassword(String email, String password) {
        if (password.isEmpty()) {
            return PassWordCheckCode.EMPTY;
        }
        
        
        return PassWordCheckCode.CORRECT;
    }

    public static void main(String[] args) {
        String email = "132sdf3@gmail.com",
                email2 = "sfkjlfjaljdsf@vndf.sds";

        System.out.println(validateEmail(email));
        System.out.println(validateEmail(email2));
    }
}
