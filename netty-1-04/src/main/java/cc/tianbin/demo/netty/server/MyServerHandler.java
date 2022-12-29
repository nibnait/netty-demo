package cc.tianbin.demo.netty.server;

import cc.tianbin.demo.netty.CommonConstants;
import io.github.nibnait.common.utils.DataUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by nibnait on 2022/12/28
 */
@Slf4j
public class MyServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext context, DatagramPacket datagramPacket) throws Exception {
        String msg = datagramPacket.content().toString(CommonConstants.CHAR_SET_UTF8);
        log.info("{} UDP服务端接收到消息: {}", CommonConstants.CURRENT_TIME(), msg);
        
        // 向客户端发送消息
        String json = DataUtils.format("我已收到你的消息: {} ", msg);
        // 由于数据报的数据是以字符数组的形式存储的，所以转数据
        byte[] bytes = json.getBytes(CommonConstants.CHAR_SET_UTF8);
        DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(bytes), datagramPacket.sender());
        // 向客户端发送消息
        context.writeAndFlush(data);
    }

}
