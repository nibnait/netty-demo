package cc.tianbin.demo.netty.rpc.protocol.spring.service.config;

import cc.tianbin.demo.netty.rpc.protocol.spring.service.SpringRpcProviderBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RpcServerProperties.class)
public class RpcServerAutoConfiguration {


    @Bean
    public SpringRpcProviderBean springRpcProviderBean(RpcServerProperties rpcServerProperties) {
        return new SpringRpcProviderBean(rpcServerProperties.getServicePort(), rpcServerProperties.getServiceAddress());
    }
}
