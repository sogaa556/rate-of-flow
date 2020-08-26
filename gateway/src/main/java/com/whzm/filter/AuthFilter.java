package com.whzm.filter;


import com.whzm.service.IRedisService;
import com.whzm.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @BelongsProject: springcloud-demo2
 * @BelongsPackage: com.example.gateway1.filter
 * @Author: hq
 * @CreateTime: 2020-08-03 16:08
 * @Description:
 */
@Component

public class AuthFilter implements GlobalFilter, Ordered {
    @Autowired
    private IRedisService redisService;

    //设置请求白名单
    private String[] whiteUrlList=new String[]{"/user/user/loginWithUsernameAndPass","/user/user/generateCode","/user/user/loginByCode",
            "/user/queryTop","/user/queryMatchSelected","/user/addEsResource","/user/queryResource"
   };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //设置请求路径
        String url=exchange.getRequest().getURI().getPath();

        //请求白名单路径 跳过验证
        if(Arrays.asList(whiteUrlList).contains(url)){
            return chain.filter(exchange);//包含则放行
        }

        //取出token
        String token=exchange.getRequest().getHeaders().getFirst("Authorization");
        //token非空校验
        if(token==null||token.isEmpty()){
            return errorResp("token无效:token中用户id信息不存在",exchange);
        }
        //验证token是否已过期
        try {
            JwtUtil.verifyToken(token);
        } catch (Exception e) {
            return errorResp("token已过期",exchange);
        }
        //token载荷信息校验
        String userId= JwtUtil.parseToken(token).get("id").asString();
        String phone= JwtUtil.parseToken(token).get("phone").asString();
        String status= JwtUtil.parseToken(token).get("status").asString();
//        System.out.println(userId);
//        System.out.println(phone);
//        System.out.println(status);
        //String roleId= JwtUtil.parseToken(token).get("roleId").asString();
        //String roleSign= JwtUtil.parseToken(token).get("roleSign").asString();
        if(userId==null||userId.isEmpty()){
            return errorResp("token无效:token中用户id信息不存在",exchange);
        }
       /* //用户级别限流
        Integer count=(Integer) redisService.get(userId);
        if(StringUtils.isEmpty(count)){
            count=1;
            redisService.set(userId,count,1L);//设置过期时间为1秒
        }else if(count<11){
            redisService.set(userId,++count);
        }else{
            return errorResp("操作频繁，稍后再试",exchange);
        }*/

        if(status.equals("1")){
            return errorResp("当前账号不可用",exchange);
        }
        //讲用户id信息存放到请求头中 后面的服务就不需要再解析token了
        ServerHttpRequest newRequest = exchange.getRequest().mutate().header("userId",userId)
                .header("phone",phone).header("status", status)
                .build();
        ServerWebExchange serverWebExchange = exchange.mutate().request(newRequest).build();
        return  chain.filter(exchange);
    }

    /**
     * 生成错误响应信息
     * @param
     * @param exchange
     * @return
     */
    private Mono<Void> errorResp(String msg, ServerWebExchange exchange) {
        ServerHttpResponse rsp=exchange.getResponse();
        rsp.setStatusCode(HttpStatus.OK);
        rsp.getHeaders().add("Content-Type","application/jsom;charset=UTF-8");

        byte[]responseByte=("{\"code\":50008,\"msg\":\"" + msg +
                "\",\"content\":null}").getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = rsp.bufferFactory().wrap(responseByte);

        return rsp.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
