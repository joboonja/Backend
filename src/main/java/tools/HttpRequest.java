package tools;

import config.RemoteURLs;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpRequest {
    public static String getRemoteData(String requestedData) throws Exception {
        String baseRemoteURL = RemoteURLs.BASE + requestedData;

        HttpURLConnection connection = (HttpURLConnection) new URL(baseRemoteURL).openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "UTF-8");


        int status = connection.getResponseCode();

        BufferedReader dataReader = new BufferedReader(status > 299 ? new InputStreamReader(connection.getErrorStream())
                : new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String line;
        StringBuffer response = new StringBuffer();

        while ((line = dataReader.readLine()) != null) {
            response.append(line);
        }
        dataReader.close();



        return response.toString();
    }
}
