package x.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import x.test.dao.entity.User;
import x.test.dao.repository.XDao;

/**
 * @author chenrong30@gome.com.cn
 * @date 2021/6/4 09:02
 */
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(App.class);
        XDao dao = ctx.getBean(XDao.class);
        User user = dao.get();
        System.out.println(user);
        dao.test();

    }

}
