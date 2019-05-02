package io.zwt.aop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloAopController {

    @GetMapping("/")
    public World hello() {
        return new World("AOP", "1002");
    }
}
