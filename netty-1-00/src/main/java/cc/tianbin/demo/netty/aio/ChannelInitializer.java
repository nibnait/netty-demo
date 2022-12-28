package cc.tianbin.demo.netty.aio;

import cc.tianbin.demo.netty.aio.server.AIOServer;
import lombok.extern.slf4j.Slf4j;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by nibnait on 2022/12/27
 */
@Slf4j
public abstract class ChannelInitializer implements CompletionHandler<AsynchronousSocketChannel, AIOServer> {

    @Override
    public void completed(AsynchronousSocketChannel channel, AIOServer attachment) {
        try {
            initChannel(channel);
        } catch (Exception e) {
            log.error("ChannelInitializer initChannel error ", e);
        } finally {
            // 在此接收客户端链接
            attachment.getServerSocketChannel().accept(attachment, this);
        }
    }

    @Override
    public void failed(Throwable exc, AIOServer attachment) {
        exc.getStackTrace();
    }

    protected abstract void initChannel(AsynchronousSocketChannel channel) throws Exception;
}
