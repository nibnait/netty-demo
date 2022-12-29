package cc.tianbin.demo.netty.bio.client;

import cc.tianbin.demo.netty.CommonConstants;
import cc.tianbin.demo.netty.bio.ChannelAdapter;
import cc.tianbin.demo.netty.bio.ChannelHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by nibnait on 2022/12/27
 */
@Slf4j
public class BIOClientHandler extends ChannelAdapter {

    public BIOClientHandler(Socket socket, Charset charset) {
        super(socket, charset);
    }

    @Override
    public void channelActive(ChannelHandler context) {
        log.info("链接报告 localAddress: {}", context.getSocket().getLocalAddress());
        context.writeAndFlush("hi! BIOClient to msg for you \n");
    }

    @Override
    public void channelRead(ChannelHandler context, Object msg) {
        log.info("{} BIOClient 收到消息 {}", CommonConstants.CURRENT_TIME(), msg);
        context.writeAndFlush("hi 我已收到你的消息 success \n");
    }
}
