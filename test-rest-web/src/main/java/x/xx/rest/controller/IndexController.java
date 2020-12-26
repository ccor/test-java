package x.xx.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import x.xx.rest.annotation.ResWrap;
import x.xx.rest.base.exception.BaseException;

/**
 */
@RestController
public class IndexController {

    @GetMapping("/ping")
    @ResWrap
    public String ping(){
        return "pong";
    }

}
