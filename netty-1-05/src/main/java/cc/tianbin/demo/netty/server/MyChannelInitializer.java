package cc.tianbin.demo.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Created by nibnait on 2022/12/29
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        // 数据解码操作
        channel.pipeline().addLast(new HttpResponseEncoder());

        // 数据编码操作
        channel.pipeline().addLast(new HttpRequestDecoder());

        // 在管道中添加我们自己的接受数据实现方法
        channel.pipeline().addLast(new MyServerHandler());
    }
}
