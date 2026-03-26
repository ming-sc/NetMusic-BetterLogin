package com.github.img.netmusicbetterlogin.api;

import com.github.tartaricacid.netmusic.api.NetWorker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class BetterNetWorker {
    public static HttpResponse get(String url, Map<String, String> requestPropertyData) throws IOException {
        StringBuilder result = new StringBuilder();
        URL urlConnect = new URL(url);
        URLConnection connection = urlConnect.openConnection(NetWorker.getProxyFromConfig());
        Collection<String> keys = requestPropertyData.keySet();
        Iterator<String> var6 = keys.iterator();

        String line;
        while (var6.hasNext()) {
            line = var6.next();
            String val = requestPropertyData.get(line);
            connection.setRequestProperty(line, val);
        }

        connection.setConnectTimeout(12000);
        connection.setDoInput(true);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }

        bufferedReader.close();
        return new HttpResponse(result.toString(), connection.getHeaderFields());
    }

    public static class HttpResponse {
        public String body;
        public Map<String, List<String>> headers;

        public HttpResponse(String body, Map<String, List<String>> headers) {
            this.body = body;
            this.headers = headers;
        }

        public List<String> getHeader(String key) {
            return headers.get(key);
        }

        public Map<String, String> getCookies() {
            List<String> header = getHeader("Set-Cookie");
            if (header == null || header.isEmpty()) {
                return Collections.emptyMap();
            }
            HashMap<String, String> cookieMap = new HashMap<>();
            header.stream().map(this::getCookieBody)
                    .filter(Objects::nonNull)
                    .forEach(cookie -> {
                        String[] parts = cookie.split("=", 2);
                        if (parts.length == 2) {
                            cookieMap.put(parts[0].trim(), parts[1].trim());
                        }
                    });
            return cookieMap;
        }

        public String getCookieBody(String rawCookie) {
            String[] parts = rawCookie.split(";");
            if (parts.length > 0) {
                return parts[0].trim();
            }
            return null;
        }
    }
}
