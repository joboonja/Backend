package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import models.services.project.ProjectService;
import models.services.user.UserService;


public class Server {
    public void startServer() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/models/data/project", new ProjectListHandler());
        server.createContext("/models/data/project/", new SpecificProjectHandler());
        server.createContext("/models/data/user/", new SpecificUserHandler());
        server.setExecutor(null);
        server.start();
    }

    private String getID(HttpExchange httpExchange) {
        StringTokenizer tokenizer = new StringTokenizer(httpExchange.getRequestURI().getPath(), "/");
        tokenizer.nextToken();
        return tokenizer.nextToken();
    }

    private void writeSuccessResponse(HttpExchange httpExchange, String response) throws IOException{
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        httpExchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream responseStream = httpExchange.getResponseBody();
        responseStream.write(response.getBytes());
        responseStream.close();
    }

    private void writeFailureResponse(HttpExchange httpExchange, String error) throws IOException{
        httpExchange.sendResponseHeaders(403, error.length());
        OutputStream errorStream = httpExchange.getResponseBody();
        errorStream.write(error.getBytes());
        errorStream.close();
    }

    class ProjectListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            try {
                String response = ProjectService.getAllProjectsHtml();
                writeSuccessResponse(httpExchange, response);
            } catch (Exception e) {
                writeFailureResponse(httpExchange, e.getMessage());
            }
        }
    }

    class SpecificProjectHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            try {
                String response = ProjectService.getProjectByIDHtml(getID(httpExchange));
                writeSuccessResponse(httpExchange, response);
            } catch (Exception e) {
                writeFailureResponse(httpExchange, e.getMessage());
            }
        }
    }

    class SpecificUserHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            try {
                String response = UserService.getUserByIDHtml(getID(httpExchange));
                writeSuccessResponse(httpExchange, response);
            } catch (Exception e) {
                writeFailureResponse(httpExchange, e.getMessage());
            }
        }
    }
}
