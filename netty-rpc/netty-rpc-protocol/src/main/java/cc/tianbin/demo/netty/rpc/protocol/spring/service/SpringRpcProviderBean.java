package cc.tianbin.demo.netty.rpc.protocol.spring.service;

import cc.tianbin.demo.netty.rpc.protocol.annotation.RemoteService;
import cc.tianbin.demo.netty.rpc.protocol.netty.NettyServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

public class SpringRpcProviderBean implements BeanPostProcessor, InitializingBean {

    private final int serverPort;
    private final String serverAddress;

    public SpringRpcProviderBean(int serverPort, String serverAddress) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 只要是加了 RemoteService 注解的，都需要发布到 map 中，跟 spring 容器对象隔离
        if (bean.getClass().isAnnotationPresent(RemoteService.class)) {
            Method[] methods = bean.getClass().getDeclaredMethods();

            for (Method method : methods) {
                String key = bean.getClass().getInterfaces()[0].getName() + "." + method.getName();

                BeanMethod beanMethod = new BeanMethod();
                beanMethod.setMethod(method);
                beanMethod.setBean(bean);
                Mediator.beanMethodMap.put(key, beanMethod);
            }
        }

        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(
                () -> new NettyServer(serverAddress, serverPort).startNettyServer()
        ).start();
    }
}
