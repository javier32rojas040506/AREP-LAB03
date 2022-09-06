package edu.eci.arep.frameworkSpring;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class HttpServer {
    public static void up(Map<String, Method> methods) throws IOException {
        ServerSocket serverSocket = null;
        //Integer puerto = new Integer(System.getenv("PORT"));
        Integer puerto =  35000;
        try {
            serverSocket = new ServerSocket(puerto);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 3500.");
            System.exit(1);
        }
        Socket clientSocket = null;
        boolean running = true;
        while (running) {
            System.out.println("====================NEW REQ==========================");
            try {
                System.out.println("Listo para recibir puerto 35000 ...");
                clientSocket = serverSocket.accept();

            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            URI path=null;
            outputLine = in.readLine();
            boolean firstLine = true;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if(firstLine){
                    try {
                        path = new URI(inputLine.split(" ")[1]);
                    } catch (URISyntaxException e){
                        //logger.getLogger(HttpServer.class.getName().log(Level.SEVERE,null, e));
                    }
                    System.out.println("parsed path: " + path);
                    System.out.println("Query: " + path.getQuery());
                    firstLine = false;
                    String responseBody = "";
                    //end points
                    for (String uri : methods.keySet()) {
                        Method m = methods.get(uri);
                        System.out.println("uri: " + uri + ", Metodo: " + m);
                        try {
                            responseBody = (String) m.invoke(null);
                            System.out.println(responseBody);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    outputLine = "HTTP/1.1 200 OK \r\n"
                            + "Content-Type: text/html \r\n"
                            + "\r\n"
                            + "\n"
                            + responseBody;
                }
                if (!in.ready()) {
                    break;
                }

            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }
}
