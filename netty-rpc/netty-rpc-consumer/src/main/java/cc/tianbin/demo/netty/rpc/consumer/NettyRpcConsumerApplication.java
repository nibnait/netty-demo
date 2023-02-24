package cc.tianbin.demo.netty.rpc.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * Created by nibnait on 2023/02/23
 */
@ComponentScan(basePackages = {"cc.tianbin.demo.netty.rpc.consumer",
        "cc.tianbin.demo.netty.rpc.protocol.annotation",
        "cc.tianbin.demo.netty.rpc.protocol.spring.reference"
})
@SpringBootApplication
public class NettyRpcConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcConsumerApplication.class, args);
    }

}
