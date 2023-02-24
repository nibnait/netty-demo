package cc.tianbin.demo.netty.rpc.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by nibnait on 2023/02/23
 */
@ComponentScan(basePackages = {"cc.tianbin.demo.netty.rpc.provider",
        "cc.tianbin.demo.netty.rpc.protocol.spring.service"
})
@SpringBootApplication
public class NettyRpcProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcProviderApplication.class, args);
    }

}
