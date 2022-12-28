package cc.tianbin.demo.netty.aio.client;

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
public class AIOClientHandler extends ChannelAdapter {
    public AIOClientHandler(AsynchronousSocketChannel channel, Charset charset) {
        super(channel, charset);
    }

    @Override
    public void channelActive(ChannelHandler ctx) {
        try {
            log.info("链接报告 localAddress: {}", ctx.getChannel().getLocalAddress());

            // 通知客户端链接建立成功
            ctx.writeAndFlush("hi! AIOClient to msg for you");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelInactive(ChannelHandler ctx) {

    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        log.info("{} AIOClient 收到消息 {}", CommonConstants.CURRENT_TIME(), msg);
        ctx.writeAndFlush("hi 我已收到你的消息 success");
    }
}
