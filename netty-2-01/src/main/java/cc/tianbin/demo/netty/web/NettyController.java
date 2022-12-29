package cc.tianbin.demo.netty.web;

import cc.tianbin.demo.netty.server.NettyServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by nibnait on 2022/12/29
 */
@RestController
@RequestMapping("/nettyserver")
public class NettyController {

    @Resource
    private NettyServer nettyServer;

    @GetMapping("/localAddress")
    public String localAddress() {
        return "nettyServer localAddress " + nettyServer.getChannel().localAddress();
    }

    @GetMapping("/isOpen")
    public String isOpen() {
        return "nettyServer isOpen " + nettyServer.getChannel().isOpen();
    }

}
