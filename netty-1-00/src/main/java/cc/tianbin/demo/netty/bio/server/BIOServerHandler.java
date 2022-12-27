package cc.tianbin.demo.netty.bio.server;

import cc.tianbin.demo.netty.bio.ChannelAdapter;
import cc.tianbin.demo.netty.bio.ChannelHandler;
import io.github.nibnait.common.utils.date.DateTimeConvertUtils;
import io.github.nibnait.common.utils.date.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by nibnait on 2022/12/27
 */
@Slf4j
public class BIOServerHandler extends ChannelAdapter {

    public BIOServerHandler(Socket socket, Charset charset) {
        super(socket, charset);
    }

    @Override
    public void channelActive(ChannelHandler ctx) {
        log.info("链接报告 localAddress: {}", ctx.getSocket().getLocalAddress());
        ctx.writeAndFlush("hi! BIOServer to msg for you \n");
    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        log.info("{} BIOServer 收到消息 {}", DateTimeConvertUtils.timeStamp2DateTimeString(DateUtils.currentTimeMillis()), msg);
        ctx.writeAndFlush("hi 我已收到你的消息 success \n");
    }
}
