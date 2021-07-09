package util.common.string;

public class StringUtil {

    private StringUtil() {
    }

    public static String standardizeName(String sequence) {
        if (sequence.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        
        String[] words = sequence.trim().toLowerCase().split("\\s+");
        for (int i = 0; i < words.length; i++) {
            sb.append(String.valueOf(words[i].charAt(0)).toUpperCase());
            sb.append(words[i].substring(1));
            sb.append(i == words.length - 1 ? "" : " ");
        }
        return sb.toString();
    }
    
    public static String standardizeString(String sequence) {
        if (sequence.isEmpty()) {
            return "";
        }
        
        return sequence.trim().replaceAll("\\s+", " ");
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
