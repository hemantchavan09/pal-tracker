package io.pivotal.pal.tracker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class WelcomeController1 {

    private String message;

    public WelcomeController1(@Value("${welcome.message}") String message) {
        this.message = message;
    }

    @GetMapping("/")
    public String sayHello() {
        return message;
    }
}
