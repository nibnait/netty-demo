package cc.tianbin.demo.netty.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * Created by nibnait on 2022/12/27
 */
public abstract class ChannelAdapter implements CompletionHandler<Integer, Object> {

    private AsynchronousSocketChannel channel;
    private Charset charset;

    private final ByteBuffer buffer = ByteBuffer.allocate(1024);
    private final long timeout = 60 * 60L;

    public ChannelAdapter(AsynchronousSocketChannel channel, Charset charset) {
        this.channel = channel;
        this.charset = charset;

        if (channel.isOpen()) {
            channelActive(new ChannelHandler(channel, charset));
        }
    }

    @Override
    public void completed(Integer result, Object attachment) {

        channel.read(buffer, timeout, TimeUnit.SECONDS, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {
                if (result == -1) {
                    try {
                        channelInactive(new ChannelHandler(channel, charset));
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                buffer.flip();
                channelRead(new ChannelHandler(channel, charset), charset.decode(buffer));
                buffer.clear();
                channel.read(buffer, timeout, TimeUnit.SECONDS, null, this);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.getStackTrace();
            }
        });
    }

    @Override
    public void failed(Throwable exc, Object attachment) {
        exc.getStackTrace();
    }

    // 链接通知抽象类
    public abstract void channelActive(ChannelHandler ctx);

    public abstract void channelInactive(ChannelHandler ctx);

    // 读取消息抽象类
    public abstract void channelRead(ChannelHandler ctx, Object msg);

}
