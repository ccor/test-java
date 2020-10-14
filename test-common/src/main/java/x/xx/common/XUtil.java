package x.xx.common;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * 常用工具类
 */
public class XUtil {
    private static final String MD5 = "MD5";
    private static final String SHA1 = "SHA1";
    private static final String PATTERN_TIME = "yyyy-MM-dd HH:mm:ss";
    private static final String PATTERN_DATE = "yyyy-MM-dd";
    private static final char[] HEX_CHARS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String APPLICATION_XML = "application/xml";

    public static boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }

    public static boolean isNotEmpty(String s) {
        return s != null && !"".equals(s);
    }

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    public <T> ArrayList<T> newArrayList() {
        return new ArrayList<>();
    }

    public String formatTime(Date date, String patten) {
        return new SimpleDateFormat(patten).format(date);
    }

    public String formatTime(Date date) {
        return new SimpleDateFormat(PATTERN_TIME).format(date);
    }

    public String formatDate(Date date) {
        return new SimpleDateFormat(PATTERN_DATE).format(date);
    }

    public String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String md5(String s) {
        byte[] b = getDigest(MD5).digest(s.getBytes());
        return encodeHexStr(b);
    }

    public static String sha1(String s) {
        byte[] b = getDigest(SHA1).digest(s.getBytes());
        return encodeHexStr(b);
    }

    public static String httpGet(String url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        return readAsString(con.getInputStream());
    }

    public static String httpPost(String url, String data, String contentType) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestProperty(CONTENT_TYPE, contentType);
        con.setDoOutput(true);
        OutputStream out = con.getOutputStream();
        out.write(data.getBytes());
        out.close();
        return readAsString(con.getInputStream());
    }

    public static String httpPostJson(String url, String jsonStr) throws IOException {
        return httpPost(url, jsonStr, APPLICATION_JSON);
    }

    public static String httpPostXml(String url, String jsonStr) throws IOException {
        return httpPost(url, jsonStr, APPLICATION_XML);
    }

    public static LocalDate date2LocalDate(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime date2LocalTime(Date date) {
        if (date == null) return null;
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static Date localDate2Date(LocalDate localDate) {
        if (localDate == null) return null;
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date localDateTime2Date(LocalDateTime localTime) {
        if (localTime == null) return null;
        return Date.from(localTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private static String readAsString(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        br.close();
        return sb.toString();
    }

    private static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Could not find MessageDigest with algorithm \"" + algorithm + "\"", ex);
        }
    }

    private static String encodeHexStr(byte[] bytes) {
        int n = bytes.length * 2;
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i = i + 2) {
            byte b = bytes[i / 2];
            sb.append(HEX_CHARS[(b >>> 0x4) & 0xf]).append(HEX_CHARS[b & 0xf]);
        }
        return sb.toString();
    }

}
