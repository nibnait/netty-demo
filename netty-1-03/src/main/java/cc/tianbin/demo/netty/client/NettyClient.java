package cc.tianbin.demo.netty.client;

import cc.tianbin.demo.netty.CommonConstants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by nibnait on 2022/12/28
 */
@Slf4j
public class NettyClient {

    public static void main(String[] args) {
        new NettyClient().connect(CommonConstants.LOCAL_IP, CommonConstants.LOCAL_PORT);
    }

    private void connect(String inetHost, int inetPort) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.AUTO_READ, true)
                    .handler(new MyChannelInitializer());

            ChannelFuture channelFuture = bootstrap.connect(inetHost, inetPort).sync();
            log.info("client start done.");
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("client start error ", e);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
