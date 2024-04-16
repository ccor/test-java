package x.xx.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2022/2/12 11:03
 */
@Slf4j
@Component
public class TokenFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();
        String token = exchange.getRequest().getHeaders().getFirst("token");
        log.info("url:{}, token:{}", url, token);
        if(token == null || token.isEmpty()) {
            return res(exchange.getResponse(), "403", "未授权访问");
        }
        // todo 验证token有效性
        return chain.filter(exchange);
    }

    private  Mono<Void> res(ServerHttpResponse resp, String code, String msg) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type","application/json;charset=UTF-8");
        Map<String, String> resObj = new HashMap<>();
        resObj.put("code", code);
        resObj.put("msg", msg);
        String res = JSON.toJSONString(resObj);
        DataBuffer buffer = resp.bufferFactory().wrap(res.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Mono.just(buffer));
    }
}
