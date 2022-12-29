package cc.tianbin.demo.netty.server;

import cc.tianbin.demo.netty.CommonConstants;
import io.github.nibnait.common.utils.DataUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by nibnait on 2022/12/29
 */
@Slf4j
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        // 打印日志
        SocketChannel channel = (SocketChannel) context.channel();
        log.info("链接报告开始");
        log.info("链接报告信息: 有一客户端连接到本服务端");
        log.info("链接报告IP: {}", channel.localAddress().getHostString());
        log.info("链接报告Port: {}", channel.localAddress().getPort());
        log.info("链接报告完毕");

        // 通知客户端链接建立成功
        String str = DataUtils.format("通知客户端链接建立成功 {} {} \n", CommonConstants.CURRENT_TIME(), channel.localAddress().getHostString());
        context.writeAndFlush(str);
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext context) {
        log.info("客户端断开链接 {}", context.channel().localAddress().toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        log.info("{} 收到消息: {}", CommonConstants.CURRENT_TIME(), msg);

        // 收到消息后，群发给客户端
        String str = DataUtils.format("服务端收到 {} {} \n", CommonConstants.CURRENT_TIME(), msg);
        context.writeAndFlush(str);
    }

    /**
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
        context.close();
        log.error("异常信息：", cause);
    }
}
