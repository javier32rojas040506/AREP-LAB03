package edu.eci.arep.frameworkSpring;

import edu.eci.arep.frameworkSpring.ResquestMapping;

import java.lang.reflect.Method;
import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.logging.Level;

public class HttpServer {
    public static void up(String uri) throws IOException {
        ServerSocket serverSocket = null;
        //Integer puerto = new Integer(System.getenv("PORT"));
        Integer puerto =  35000;
        try {
            serverSocket = new ServerSocket(puerto);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35006.");
            System.exit(1);
        }
        Socket clientSocket = null;
        boolean running = true;
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String outputLine = "";
        while (running) {
            System.out.println("====================NEW REQ==========================");
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            System.out.println("parsed path: " + uri);
            String responseBody = "";
            Method m = Spring.services.get(uri);
            System.out.println(m);
            if(uri.startsWith("/")){
                responseBody = "Hello " + uri.substring(5);
            }

            outputLine = "HTTP/1.1 200 OK \r\n"
                    + "Content-Type: text/html \r\n"
                    + "\r\n"
                    + "\n"
                    + responseBody;
        }

            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
        }

}
