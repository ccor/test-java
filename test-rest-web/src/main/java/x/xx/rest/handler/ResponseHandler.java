package x.xx.rest.handler;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import x.xx.rest.annotation.ResWrap;
import x.xx.rest.result.RestResultWrapper;

/**
 * 正常返回响应包装
 */
@RestControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice<Object> {

    /**
     * 本实现判断是否要统一包装返回对象，需有注解 @ResWrap
     *
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        // 加上@ResWrap才包装
        return returnType.hasMethodAnnotation(ResWrap.class);
    }

    /**
     * 本实现自动包装Controller的原始返回对象为统一的Res对象
     *
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        return RestResultWrapper.success(body);
    }

}
