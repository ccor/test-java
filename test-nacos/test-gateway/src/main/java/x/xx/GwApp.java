package x.xx;

import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @date 2021/8/28 12:29
 */
@SpringBootApplication
@EnableNacosConfig
public class GwApp {
    public static void main(String[] args) {
        SpringApplication.run(GwApp.class);
    }
}
