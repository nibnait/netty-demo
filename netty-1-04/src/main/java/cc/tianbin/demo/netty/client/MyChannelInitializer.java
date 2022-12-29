package cc.tianbin.demo.netty.client;

import cc.tianbin.demo.netty.CommonConstants;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by nibnait on 2022/12/28
 */
@Slf4j
public class MyChannelInitializer extends ChannelInitializer<NioDatagramChannel> {

    @Override
    protected void initChannel(NioDatagramChannel nioDatagramChannel) throws Exception {
        ChannelPipeline pipeline = nioDatagramChannel.pipeline();

        pipeline.addLast("stringDecoder", new StringDecoder(CommonConstants.CHAR_SET_UTF8));
        pipeline.addLast(new MyClientHandler());
    }
}
