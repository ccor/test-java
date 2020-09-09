package x.xx.util;

public class StringUtil {
    public static String join(int[] arr){
        StringBuilder sb = new StringBuilder("[");
        if(arr.length > 0) sb.append(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            sb.append(",").append(arr[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}
