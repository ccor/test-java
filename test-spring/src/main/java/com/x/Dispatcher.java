package com.x;

import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 分发器
 * @date 2024/4/9 20:48
 */
public class Dispatcher {
    interface IHandler {
        void handle();
    }
    @interface HandlerType {
        String value();
    }
    ApplicationContext ctx;
    Map<String, IHandler> handlers = new HashMap<>();

    @PostConstruct
    public void init() {
        Map<String, IHandler> beans = ctx.getBeansOfType(IHandler.class);
        beans.forEach((name, bean) -> {
            Class<?> handlerClazz = ClassUtils.getUserClass(bean.getClass());
            Assert.notNull(handlerClazz, "handlerType is null!");
            HandlerType handlerType = AnnotatedElementUtils.findMergedAnnotation(handlerClazz, HandlerType.class);
            handlers.put(handlerType.value(), bean);
        });
    }
}
