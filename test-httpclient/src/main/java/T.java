import java.util.ArrayList;
import java.util.List;

public class T {
    public static void main(String[] args) {
        String s = new T().longestCommonPrefix(new String[]{"abca", "aba", "abab"});
        System.out.println(s);
    }

    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        int len = strs[0].length();
        for (int i = 0; i < len; i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || c != strs[j].charAt(i))
                    return strs[0].substring(0, i);
            }
        }
        return strs[0];
    }


}
