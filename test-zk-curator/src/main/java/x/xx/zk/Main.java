package x.xx.zk;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import x.xx.zk.curator.ZkWatcher;

@Log4j2
@SpringBootApplication(
        scanBasePackages = {"x.xx"}
)
public class Main {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Main.class);
        ApplicationContext ctx = application.run(args);
        ZkWatcher zkWatcher = ctx.getBean(ZkWatcher.class);
        try {
            String s = zkWatcher.getNodeData("/xx");
            log.info("/xx: {}", s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
