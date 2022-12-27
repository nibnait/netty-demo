package cc.tianbin.demo.netty.aio.server;

import cc.tianbin.demo.netty.CommonConstants;
import cc.tianbin.demo.netty.aio.ChannelInitializer;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * Created by nibnait on 2022/12/27
 */
public class AioServerChannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(AsynchronousSocketChannel channel) throws Exception {
        channel.read(ByteBuffer.allocate(1024), 10, TimeUnit.SECONDS, null,
                new AIOServerHandler(channel, Charset.forName(CommonConstants.CHAR_SET)));
    }
}
