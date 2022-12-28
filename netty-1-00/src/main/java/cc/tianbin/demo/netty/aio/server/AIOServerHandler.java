package cc.tianbin.demo.netty.aio.server;

import cc.tianbin.demo.netty.CommonConstants;
import cc.tianbin.demo.netty.aio.ChannelAdapter;
import cc.tianbin.demo.netty.aio.ChannelHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;

/**
 * Created by nibnait on 2022/12/27
 */
@Slf4j
public class AIOServerHandler extends ChannelAdapter {
    public AIOServerHandler(AsynchronousSocketChannel channel, Charset charset) {
        super(channel, charset);
    }

    @Override
    public void channelActive(ChannelHandler ctx) {
        try {
            log.info("链接报告 localAddress: {}", ctx.getChannel().getLocalAddress());
            ctx.writeAndFlush("hi! AIOServer to msg for you");
        } catch (IOException e) {
            log.error("aio server channelActive writeAndFlush error ", e);
        }
    }

    @Override
    public void channelInactive(ChannelHandler ctx) {

    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        log.info("{} AIOServer 收到消息 {}", CommonConstants.CURRENT_TIME(), msg);
        ctx.writeAndFlush("hi 我已收到你的消息 success");
    }
}
