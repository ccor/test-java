package x.xx.crtl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2021/2/2 21:18
 */
@RestController
public class UserController {

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }

}
