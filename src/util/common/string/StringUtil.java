package util.common.string;

import util.messages.Messages;

public class StringUtil {

    private StringUtil() {
    }

    public static String standardizeName(String sequence) {
        if (sequence.isEmpty()) {
            return "";
        }
        String result;
        result = sequence.trim().toLowerCase();
        result = result.replaceAll("\\s+", " ");
        String[] temp = result.split(" ");
        result = "";
        for (int i = 0; i < temp.length; i++) {
            result += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1) + " ";
        }
        return result.trim();
    }

    public static String standardizeString(String sequence) {
        if (sequence.isEmpty()) {
            return "";
        }
        String result;
        result = sequence.trim();
        result = result.replaceAll("\\s+", " ");
        return result;
    }

    public static boolean haveNonLetterInName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isAlphabetic(name.charAt(i)) && name.charAt(i) != ' ') {
                return true;
            }
        }
        return false;
    }

    public static boolean haveNonLetterAndDigitInName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetterOrDigit(name.charAt(i)) && name.charAt(i) != ' ') {
                return true;
            }
        }
        return false;
    }

}
