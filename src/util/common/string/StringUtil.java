package util.common.string;

public class StringUtil {

    private StringUtil() {
    }
    
    public static String getCapitalizeWord(String sequence) {
        String ret = "";
        for (String word : sequence.split("\\s")) {
            ret += (word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase() + " ");
        }
        return ret.trim();
    }
}
