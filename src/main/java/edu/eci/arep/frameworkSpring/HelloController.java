package edu.eci.arep.frameworkSpring;

public class HelloController {

    @ResquestMapping("/")
    public static String index() {
        return "Greetings from Spring Boot!";
    }
}
