package cc.tianbin.demo.netty.client;

import cc.tianbin.demo.netty.CommonConstants;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * Created by nibnait on 2022/12/28
 */
@Slf4j
public class NettyClient {

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioDatagramChannel.class)
                    .handler(new MyChannelInitializer());

            Channel channel = bootstrap.bind(CommonConstants.LOCAL_PORT_7398).sync().channel();

            // 向目标端口发送信息
            channel.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer("hi 7397, 我是客户端", CommonConstants.CHAR_SET_UTF8),
                    new InetSocketAddress(CommonConstants.LOCAL_IP, CommonConstants.LOCAL_PORT)
            )).sync();
            channel.closeFuture().await();
        } catch (Exception e) {
            log.error("client start error ", e);
        } finally {
            group.shutdownGracefully();
        }
    }
}
