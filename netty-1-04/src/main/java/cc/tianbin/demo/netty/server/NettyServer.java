package cc.tianbin.demo.netty.server;

import cc.tianbin.demo.netty.CommonConstants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by nibnait on 2022/12/28
 */
@Slf4j
public class NettyServer {

    public static void main(String[] args) {
        // 配置服务端 NIO 线程组
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioDatagramChannel.class)
                    // 广播
                    .option(ChannelOption.SO_BROADCAST, true)
                    // 设置UDP读缓冲区为 2M
                    .option(ChannelOption.SO_RCVBUF, 2048 * 1024)
                    // 设置UDP写缓冲区为 1M
                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024)
                    .handler(new MyChannelInitializer());

            ChannelFuture channelFuture = bootstrap.bind(CommonConstants.LOCAL_PORT).sync();
            log.info("server start done.");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server start error ", e);
        } finally {
            group.shutdownGracefully();
        }

    }

}
