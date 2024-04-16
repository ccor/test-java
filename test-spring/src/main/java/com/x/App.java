package com.x;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

/**
 * @date 2022/6/22 17:36
 */
@SpringBootApplication
// @Import(Aop.class)
public class App {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(App.class);
        IUser user = ctx.getBean(IUser.class);
        user.info("");
        // user.info(new Object());
    }
}
