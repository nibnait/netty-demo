package cc.tianbin.demo.netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * Created by nibnait on 2022/12/28
 */
@Slf4j
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        // 解码器
        // 基于换行符号
        channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 基于指定字符串【换行符，这样功能等同于LineBasedFrameDecoder】
//        channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, false, Delimiters.lineDelimiter()));
        // 基于最大长度
//        channel.pipeline().addLast(new FixedLengthFrameDecoder(4));

        // 解码转String，注意调整自己的编码格式 UTF-8
        channel.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));
        // 解码转String，注意调整自己的编码格式 UTF-8
        channel.pipeline().addLast(new StringEncoder(StandardCharsets.UTF_8));

        //在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyClientHandler());

    }
}
