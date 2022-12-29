package cc.tianbin.demo.netty.nio.client;

import cc.tianbin.demo.netty.CommonConstants;
import cc.tianbin.demo.netty.nio.ChannelAdapter;
import cc.tianbin.demo.netty.nio.ChannelHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.charset.Charset;

/**
 * Created by nibnait on 2022/12/27
 */
@Slf4j
public class NIOClientHandler extends ChannelAdapter {

    public NIOClientHandler(Selector selector, Charset charset) {
        super(selector, charset);
    }

    @Override
    public void channelActive(ChannelHandler context) {
        try {
            log.info("链接报告 localAddress: {}", context.getChannel().getLocalAddress());
            context.writeAndFlush("hi! NIOClient to msg for you");
        } catch (IOException e) {
            log.error("nio client channelActive writeAndFlush error ", e);
        }
    }

    @Override
    public void channelRead(ChannelHandler context, Object msg) {
        log.info("{} NIOClient 收到消息 {}", CommonConstants.CURRENT_TIME(), msg);
        context.writeAndFlush("hi 我已收到你的消息 success");
    }
}
