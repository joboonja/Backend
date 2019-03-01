package tools;

import com.sun.net.httpserver.HttpExchange;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

public class HttpTokenizer {
    public static String getID(HttpServletRequest request) {
        StringTokenizer tokenizer = new StringTokenizer(request.getPathInfo(), "/");
        tokenizer.nextToken();
        return tokenizer.nextToken();
    }
}
