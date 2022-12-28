package cc.tianbin.demo.netty.server;

import cc.tianbin.demo.netty.codec.MyDecoder;
import cc.tianbin.demo.netty.codec.MyEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by nibnait on 2022/12/28
 */
@Slf4j
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        // 自定义解码器
        channel.pipeline().addLast(new MyDecoder());
        // 自定义编码器
        channel.pipeline().addLast(new MyEncoder());

        //在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyServerHandler());

    }
}
