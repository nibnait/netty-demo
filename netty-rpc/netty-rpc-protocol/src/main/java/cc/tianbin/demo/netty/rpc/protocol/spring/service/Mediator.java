package cc.tianbin.demo.netty.rpc.protocol.spring.service;

import cc.tianbin.demo.netty.rpc.protocol.core.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class Mediator {

    public static Map<String, BeanMethod> beanMethodMap = new ConcurrentHashMap<>();

    private volatile static Mediator instance = null;

    private Mediator() {

    }

    public static Mediator getInstance() {
        if (instance == null) {
            synchronized (Mediator.class) {
                if (instance == null) {
                    instance = new Mediator();
                }
            }
        }
        return instance;
    }

    public Object processor(RpcRequest rpcRequest) {
        // 以类的全名称和方法名作为key
        String key = rpcRequest.getClassName() + "." + rpcRequest.getMethodName();

        BeanMethod beanMethod = beanMethodMap.get(key);
        if (beanMethod == null) {
            return null;
        }

        Object bean = beanMethod.getBean();
        Method method = beanMethod.getMethod();
        try {
            return method.invoke(bean, rpcRequest.getParams());
        } catch (Exception e) {
            log.error("Mediator processor error ", e);
        }
        return null;
    }
}
