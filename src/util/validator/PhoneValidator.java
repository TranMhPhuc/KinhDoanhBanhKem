package util.validator;

public class PhoneValidator {

    public enum PhoneValidateResult {
        EMPTY,
        ERROR_FORMAT,
        INVALLID,
        PASS
    }

    private static int PHONE_NUM_VALID = 10;

    private PhoneValidator() {
    }

    public static void setValidDigitNum(int num) {
        if (num < 20) {
            PHONE_NUM_VALID = num;
        } else {
            PHONE_NUM_VALID = 20;
        }
    }

    public static int getPhoneNumValid() {
        return PHONE_NUM_VALID;
    }

    public static PhoneValidateResult validate(String phoneNumText) {
        String input = phoneNumText.trim();

        if (input.isEmpty()) {
            return PhoneValidateResult.EMPTY;
        }

        try {
            Long.parseLong(input);
        } catch (NumberFormatException ex) {
            return PhoneValidateResult.ERROR_FORMAT;
        }

        if (input.length() != PHONE_NUM_VALID) {
            return PhoneValidateResult.INVALLID;
        }

        return PhoneValidateResult.PASS;
    }

}
