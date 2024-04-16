package x.xx.cfg;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

/**
 * @date 2022/8/25 16:36
 */
@Slf4j
@Configuration
public class DynamicRouteConfig {

    @Autowired
    private RouteDefinitionWriter writer;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Value("${nacos.config.server-addr}")
    private String serverAddr;

    @Value("${nacos.config.namespace}")
    private String namespace;

    @PostConstruct
    public void init() {
        log.info("init gw config, serverAddr:{}, namespace:{}", serverAddr, namespace);
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.NAMESPACE, namespace);
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, serverAddr);

        try {
            ConfigService configService = NacosFactory.createConfigService(properties);
            // configService.addListener("test-gw_route", "DEFAULT_GROUP", new Listener() {
            //     @Override
            //     public Executor getExecutor() {
            //         return null;
            //     }
            //
            //     @Override
            //     public void receiveConfigInfo(String configInfo) {
            //         updateConfig(configInfo);
            //     }
            // });
            String s = configService.getConfig("test-gw_route", "DEFAULT_GROUP", 5000);
            updateConfig(s);

        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

    @NacosConfigListener(dataId = "test-gw_route")
    public void updateConfig(String content) {
        log.info("nacos 动态路由更新： {}", content);
        try {
            List<RouteDefinition> definitionList =  getRouteDefinitions(content);
            definitionList.forEach(routeDefinition -> {
                log.info("动态路由配置: {}", routeDefinition);
                writer.delete(Mono.just(routeDefinition.getId()));
                writer.save(Mono.just(routeDefinition)).subscribe();
            });
            if(!definitionList.isEmpty()) {
                publisher.publishEvent(new RefreshRoutesEvent(this));
            }
        } catch (Exception e) {
            log.error("更新动态路由配置异常: ", e);
        }
    }

    private List<RouteDefinition> getRouteDefinitions(String content) {
        // 如果文件是json，这里则直接把内容转会为json即可
        return JSON.parseArray(content, RouteDefinition.class);
        // return JSON.parseArray(JSON.toJSONString(YamlHelper.getInstance().load(content)), RouteDefinition.class);
    }
}
