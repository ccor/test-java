package x.xx;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import x.xx.data.entity.UserEntity;
import x.xx.data.mapper.UserMapper;

import java.util.List;

/**
 * @author ccor
 * @date 2020/12/24 20:44
 */
@SpringBootApplication
@Slf4j
@MapperScan("x.xx.data.mapper")

public class MyBatisPlusApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(MyBatisPlusApplication.class);
        UserMapper mapper = ctx.getBean(UserMapper.class);
        List<UserEntity> userEntityList = mapper.selectList(null);
        log.info("{}", userEntityList);
    }
}
