package x.xx.zk.curator;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode.BUILD_INITIAL_CACHE;

@Log4j2
public class ZkWatcher extends ApplicationObjectSupport implements InitializingBean, DisposableBean {

    private static final String ZK_NODE_PATH = "/";
    private static final List<PathChildrenCacheEvent.Type> CHANGE_EVENT_TYPES = Arrays.asList(
            PathChildrenCacheEvent.Type.CHILD_ADDED,
            PathChildrenCacheEvent.Type.CHILD_UPDATED,
            PathChildrenCacheEvent.Type.CHILD_REMOVED
    );

    @Setter
    private String zkAddress;

    @Setter
    private String namespace;

    private CuratorFramework client;
    private PathChildrenCache pathChildrenCache;
    private Map<String, String> cacheMap = new ConcurrentHashMap<>();
    private Map<String, List<ZkNodeChangeHandler>> handlersMap = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        initHandlerMethods();
        initZkListener();
    }

    @Override
    public void destroy() throws Exception {
        CloseableUtils.closeQuietly(pathChildrenCache);
        CloseableUtils.closeQuietly(client);
    }

    public void setNodeData(String path, String data) throws Exception {
        if (client.checkExists().forPath(path) == null) {
            client.create().creatingParentsIfNeeded().forPath(path, data.getBytes());
        } else {
            client.setData().forPath(path, data.getBytes());
        }
    }

    public String getNodeData(String path) throws Exception {
        if (client.checkExists().forPath(path) == null) {
            return null;
        }else{
            byte[] b = client.getData().forPath(path);
            return b != null ? new String(b) : null;
        }
    }

    /**
     * 初始化ZooKeeper节点监听
     */
    private void initZkListener() {
        client = CuratorFrameworkFactory.builder()
                .connectString(zkAddress)
                .retryPolicy(new RetryUntilElapsed(60000, 1000))
                .namespace(namespace)
                .build();
        client.start();

        pathChildrenCache = new PathChildrenCache(client, ZK_NODE_PATH, true, new ThreadFactory() {
            final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "ZkWatcher-Thread-" + threadNumber.getAndIncrement());
            }
        });
        try {
            pathChildrenCache.start(BUILD_INITIAL_CACHE);
            log.info("ZkWatcher started.");
            for (ChildData cd : pathChildrenCache.getCurrentData()) {
                cacheMap.put(cd.getPath(), str(cd.getData()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pathChildrenCache.getListenable().addListener(this::pathChildrenCacheEventHandle);
    }

    /**
     * 子节点变动通知处理器的收集
     * 注解了`@ZkNodeChange`的方法
     */
    private void initHandlerMethods() {
        ApplicationContext ctx = getApplicationContext();
        String[] beanNames = ctx.getBeanNamesForType(Object.class);
        for (String beanName : beanNames) {
            Class<?> handlerType = ctx.getType(beanName);
            Class<?> userType = ClassUtils.getUserClass(handlerType);
            Map<Method, String> methods = MethodIntrospector.selectMethods(userType, this::getPath);
            Object bean = ctx.getBean(beanName);
            methods.forEach(((method, path) -> {
                registerHandlerMethod(path, bean, method);
            }));
        }
    }

    private void pathChildrenCacheEventHandle(CuratorFramework client,
                                              PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
        log.warn(pathChildrenCacheEvent.getType());
        if (CHANGE_EVENT_TYPES.contains(pathChildrenCacheEvent.getType())) {
            for (ChildData cd : pathChildrenCache.getCurrentData()) {
                String path = cd.getPath();
                String value = str(cd.getData());
                log.warn("{}:{}", path, value);
                List<ZkNodeChangeHandler> handlers = handlersMap.get(path);
                if (handlers != null && !Objects.equals(cacheMap.get(path), value)) {
                    for (ZkNodeChangeHandler handler : handlers) {
                        handler.bridgeMethod.invoke(handler.bean, value);
                    }
                }
            }
        }
    }

    /**
     * 尝试获取含`@ZkNodeChange`的元素上的注解实例值
     *
     * @param element
     * @return 如果未找到返回null
     */
    private String getPath(AnnotatedElement element) {
        ZkNodeChange zkNodeChange =
                AnnotatedElementUtils.findMergedAnnotation(element, ZkNodeChange.class);
        return zkNodeChange != null ? zkNodeChange.path() : null;
    }

    /**
     * 注册zk节点变动处理方法
     *
     * @param path
     * @param bean
     * @param method
     */
    private void registerHandlerMethod(String path, Object bean, Method method) {
        log.info("ZkWatch register: {} -> {}.{}", path, method.getDeclaringClass().getName(), method.getName());
        ZkNodeChangeHandler handler = new ZkNodeChangeHandler();
        handler.bean = bean;
        handler.method = method;
        handler.bridgeMethod = BridgeMethodResolver.findBridgedMethod(method);
        Class<?>[] parameterTypes = handler.bridgeMethod.getParameterTypes();
        if (parameterTypes.length != 1 || parameterTypes[0] != String.class) {
            log.warn("Mismatch Parameter zkNodeChangeHandler:{},", method);
        }
        List<ZkNodeChangeHandler> list = handlersMap.computeIfAbsent(path, k -> new ArrayList<>());
        list.add(handler);
    }

    private String str(byte[] b) {
        return b == null ? "" : new String(b);
    }

    static class ZkNodeChangeHandler {
        Object bean;
        Method method;
        Method bridgeMethod;
    }

}
