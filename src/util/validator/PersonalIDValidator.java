package util.validator;

public class PersonalIDValidator {

    public enum PersonalIDValidateResult {
        EMPTY,
        ERROR_FORMAT,
        INVALLID,
        PASS
    };

    private static int PERSONAL_ID_DIGIT_NUM_VALID = 12;

    private PersonalIDValidator() {
    }

    public static void setValidDigitNum(int num) {
        if (num < 20) {
            PERSONAL_ID_DIGIT_NUM_VALID = num;
        } else {
            PERSONAL_ID_DIGIT_NUM_VALID = 20;
        }

    }

    public static int getPhoneNumValid() {
        return PERSONAL_ID_DIGIT_NUM_VALID;
    }

    public static PersonalIDValidateResult validate(String personalIDText) {
        String input = personalIDText.trim();

        if (input.isEmpty()) {
            return PersonalIDValidateResult.EMPTY;
        }

        try {
            Long.parseLong(input);
        } catch (NumberFormatException ex) {
            return PersonalIDValidateResult.ERROR_FORMAT;
        }

        if (input.length() != PERSONAL_ID_DIGIT_NUM_VALID) {
            return PersonalIDValidateResult.INVALLID;
        }

        return PersonalIDValidateResult.PASS;
    }

}
