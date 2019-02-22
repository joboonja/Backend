package server;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.StringTokenizer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.xml.ws.http.HTTPException;

public class Server {
    private void startServer() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/project", new ProjectListHandler());
        server.createContext("/project/", new SpecificProjectHandler());
        server.createContext("/user/", new SpecificUserHandler());
        server.setExecutor(null);
        server.start();
    }

    private String getID(HttpExchange httpExchange) {
        StringTokenizer tokenizer = new StringTokenizer(httpExchange.getRequestURI().getPath(), "/");
        tokenizer.nextToken();
        return tokenizer.nextToken();
    }

    private void writeSuccessResponse(HttpExchange httpExchange, String response) throws IOException{
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream responseStream = httpExchange.getResponseBody();
        responseStream.write(response.getBytes());
        responseStream.close();
    }

    class ProjectListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = ProjectService.getAllProjectsHtml();
            writeSuccessResponse(httpExchange, response);
        }
    }

    class SpecificProjectHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = ProjectService.getProjectByIdHtml(getID(httpExchange));
            writeSuccessResponse(httpExchange, response);
        }
    }

    class SpecificUserHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = UserService.getUserByIdHtml(getID(httpExchange));
            writeSuccessResponse(httpExchange, response);
        }
    }
}
