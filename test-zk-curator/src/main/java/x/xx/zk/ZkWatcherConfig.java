package x.xx.zk;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import x.xx.zk.curator.ZkWatcher;


@Configuration
@ConfigurationProperties(prefix = "zookeeper")
public class ZkWatcherConfig {

    @Setter
    private String address;

    @Setter
    private String namespace;

    @Bean
    public ZkWatcher zkWatcher(){
        ZkWatcher watcher = new ZkWatcher();
        watcher.setZkAddress(address);
        watcher.setNamespace(namespace);
        return watcher;
    }
}
