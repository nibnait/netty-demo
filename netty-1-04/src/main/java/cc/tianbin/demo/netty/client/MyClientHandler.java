package cc.tianbin.demo.netty.client;

import cc.tianbin.demo.netty.CommonConstants;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by nibnait on 2022/12/28
 */
@Slf4j
public class MyClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        String msg = datagramPacket.content().toString(CommonConstants.CHAR_SET_UTF8);
        log.info("{} UDP客户端收到消息: {}", CommonConstants.CURRENT_TIME(), msg);
    }

}
