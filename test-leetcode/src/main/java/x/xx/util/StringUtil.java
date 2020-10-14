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

    public static String join(int[][] arr){
        StringBuilder sb = new StringBuilder("[");
        if(arr.length > 0) sb.append(join(arr[0]));
        for (int i = 1; i < arr.length; i++) {
            sb.append(",").append(join(arr[i]));
        }
        sb.append("]");
        return sb.toString();
    }

    public static int[] ofIntArr1(String s) {
        if(s == null) return null;
        if("[]".equals(s)) return new int[0];
        if(!s.matches("\\[(-?\\d+,)*-?\\d+\\]")){
            throw new IllegalArgumentException("String is not match int array format.");
        }
        String[] t = s.substring(1, s.length()-1).split(",");
        int n = t.length;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(t[i]);
        }
        return arr;
    }
    public static int[][] ofIntArr2(String s) {
        if(s.startsWith("[[") && s.endsWith("]]")){
            String[] t = s.substring(2, s.length()-2).split("\\] *, *\\[");
            int[][] arr = new int[t.length][t[0].split(",").length];
            for (int i = 0; i < arr.length; i++) {
                String[] k = t[i].split(",");
                for (int j = 0; j < k.length; j++) {
                    arr[i][j] = Integer.parseInt(k[j]);
                }
            }
            return arr;
        }else throw new IllegalArgumentException("String is not match int array format.");
    }

}
