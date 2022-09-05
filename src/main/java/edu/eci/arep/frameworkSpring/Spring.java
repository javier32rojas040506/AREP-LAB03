package edu.eci.arep.frameworkSpring;

import edu.eci.arep.frameworkTest.Test;

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
                    System.out.println(uri + ":" +  m);
                    //services.put(uri, m);
                    HttpServer.up(uri);
                } catch (Throwable ex) {
                    System.out.printf("Test %s failed: %s %n", m, ex.getCause());
                }}}
        //System.out.printf("Passed: %d, Failed %d%n", passed, failed);
    }
}
