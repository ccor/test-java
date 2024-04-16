package com.x;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @date 2022/7/28 14:17
 */
@Configuration
public class T2 extends T1{

    @Bean
    public IUser user(){
        UserImpl impl = new UserImpl();
        impl.setName("user instance2");
        return impl;
    }
}
