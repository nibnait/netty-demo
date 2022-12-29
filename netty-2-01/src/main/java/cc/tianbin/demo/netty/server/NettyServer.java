package cc.tianbin.demo.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * Created by nibnait on 2022/12/29
 */
@Component
@Slf4j
public class NettyServer {

    // 配置 服务端 NIO 线程组
    private final EventLoopGroup parentGroup = new NioEventLoopGroup();
    private final EventLoopGroup childGroup = new NioEventLoopGroup();

    @Getter
    private Channel channel;

    public ChannelFuture bing(InetSocketAddress address) {
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new MyChannelInitializer());

            channelFuture = serverBootstrap.bind(address).syncUninterruptibly();
            channel = channelFuture.channel();
        } catch (Exception e) {
            log.error("NettyServer bing error ", e);
        } finally {
            if (channelFuture != null && channelFuture.isSuccess()) {
                log.info("server start done.");
            } else {
                log.error("server start error");
            }
        }

        return channelFuture;
    }

    public void destroy() {
        if (channel == null) {
            return;
        }

        channel.close();
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }

}
