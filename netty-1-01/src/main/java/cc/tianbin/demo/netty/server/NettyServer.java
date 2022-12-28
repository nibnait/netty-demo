package cc.tianbin.demo.netty.server;

import cc.tianbin.demo.netty.CommonConstants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by nibnait on 2022/12/28
 */
@Slf4j
public class NettyServer {

    public static void main(String[] args) {
        new NettyServer().bing(CommonConstants.LOCAL_PORT);
    }

    private void bing(int port) {
        // 配置服务端 NIO 线程组
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        NioEventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parentGroup, childGroup)
                    // 非阻塞
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new MyChannelInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

            log.info("server start done.");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server start error ", e);
        } finally {
            childGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
        }

    }

}
