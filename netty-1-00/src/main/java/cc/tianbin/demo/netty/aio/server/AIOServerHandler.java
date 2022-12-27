package cc.tianbin.demo.netty.aio.server;

import cc.tianbin.demo.netty.aio.ChannelAdapter;
import cc.tianbin.demo.netty.aio.ChannelHandler;
import io.github.nibnait.common.utils.date.DateTimeConvertUtils;
import io.github.nibnait.common.utils.date.DateUtils;
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
            e.printStackTrace();
        }
    }

    @Override
    public void channelInactive(ChannelHandler ctx) {

    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        log.info("{} AIOServer 收到消息 {}", DateTimeConvertUtils.timeStamp2DateTimeString(DateUtils.currentTimeMillis()), msg);
        ctx.writeAndFlush("hi 我已收到你的消息 success");
    }
}
