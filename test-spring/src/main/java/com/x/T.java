package com.x;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @date 2022/7/28 14:15
 */

public  class T {
    @Bean
    public IUser user(){
        UserImpl impl = new UserImpl();
        impl.setName("user instance1");
        return impl;
    }
}
