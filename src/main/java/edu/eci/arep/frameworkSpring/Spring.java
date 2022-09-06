package edu.eci.arep.frameworkSpring;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Spring {
    static Map<String, Method> services = new HashMap<String, Method>();
    public static void main(String[] args) throws Exception {
        String className = args[0];
         Class c = Class.forName(className);
        for (Method m : c.getMethods()) {
            if (m.isAnnotationPresent(ResquestMapping.class)) {
                try {
                    String uri  = m.getAnnotation(ResquestMapping.class).value();
                    System.out.println(uri);
                    System.out.println("Uri: "+ uri + " Method: " +  m);
                    services.put(uri, m);
                    HttpServer.up(services);
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }}}
        //System.out.printf("Passed: %d, Failed %d%n", passed, failed);
    }
}
