package x.xx.httpclient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class HttpClient {

    Map<String, String> headers = new HashMap<>();
    String baseURL = "";

    String get(String uri) {
        String url = uri.startsWith("http") ? uri : baseURL + uri;
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(baseURL.isEmpty() ? url : baseURL + url).openConnection();
            for(Map.Entry<String, String> entry : headers.entrySet()) {
                con.setRequestProperty(entry.getKey(), entry.getValue());
                con.setRequestMethod("GET");
                con.getInputStream();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    String post(String url, String data) {
        return null;
    }


}
