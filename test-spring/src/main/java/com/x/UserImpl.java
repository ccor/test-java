package com.x;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @date 2022/6/22 17:33
 */
// @Service
@Slf4j
public class UserImpl implements IUser{
    @Setter
    private String name;

    // @Override
    // public void info(String... s) {
    //     System.out.println("-------------> impl");
    //     log.info("--------------> impl:{}", name);
    // }

    public void info(Object o) {
        System.out.println("-------------> impl");
        log.info("--------------> impl:{}", name);
    }
}
