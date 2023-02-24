package cc.tianbin.demo.netty.rpc.protocol.spring.reference.config;

import cc.tianbin.demo.netty.rpc.protocol.spring.reference.SpringRpcReferencePostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RpcClientAutoConfiguration implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public SpringRpcReferencePostProcessor postProcessor() {
        RpcClientProperties rpcClientProperties = new RpcClientProperties();
        rpcClientProperties.setServicePort(Integer.parseInt(this.environment.getProperty("rpc.client.servicePort")));
        rpcClientProperties.setServiceAddress(this.environment.getProperty("rpc.client.serviceAddress"));
        return new SpringRpcReferencePostProcessor(rpcClientProperties);
    }
}
