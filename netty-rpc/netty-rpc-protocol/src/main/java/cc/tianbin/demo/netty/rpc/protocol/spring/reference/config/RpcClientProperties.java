package cc.tianbin.demo.netty.rpc.protocol.spring.reference.config;

import lombok.Data;

@Data
public class RpcClientProperties {

    private String serviceAddress;

    private int servicePort;
}
