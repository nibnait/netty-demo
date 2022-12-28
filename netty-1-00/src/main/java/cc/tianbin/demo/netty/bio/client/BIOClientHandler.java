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
    public void channelActive(ChannelHandler ctx) {
        log.info("链接报告 localAddress: {}", ctx.getSocket().getLocalAddress());
        ctx.writeAndFlush("hi! BIOClient to msg for you \n");
    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        log.info("{} BIOClient 收到消息 {}", CommonConstants.CURRENT_TIME(), msg);
        ctx.writeAndFlush("hi 我已收到你的消息 success \n");
    }
}
