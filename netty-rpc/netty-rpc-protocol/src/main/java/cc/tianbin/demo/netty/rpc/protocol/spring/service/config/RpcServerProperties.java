package cc.tianbin.demo.netty.rpc.protocol.spring.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "rpc.provider")
public class RpcServerProperties {

    private int servicePort;
    private String serviceAddress;
}
