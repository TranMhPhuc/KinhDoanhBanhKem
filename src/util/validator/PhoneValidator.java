package util.validator;

public class PhoneValidator {
    
    public enum PhoneValidateResult {
        EMPTY,
        ERROR_FORMAT,
        INVALLID,
        PASS
    }
    
    public static final int PHONE_NUM_VALID = 10;

    private PhoneValidator() {
    }

    public static PhoneValidateResult validate(String phoneNumText) {
        String input = phoneNumText.trim();
        
        if (input.isEmpty()) {
            return PhoneValidateResult.EMPTY;
        }
        
        try {
            Long phoneNum = Long.parseLong(input);
        } catch (NumberFormatException ex) {
            return PhoneValidateResult.ERROR_FORMAT;
        }
        
        if (input.length() != 10) {
            return PhoneValidateResult.INVALLID;
        }
        
        return PhoneValidateResult.PASS;
    }
    
}
