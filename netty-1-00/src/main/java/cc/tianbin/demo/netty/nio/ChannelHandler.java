package cc.tianbin.demo.netty.nio;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created by nibnait on 2022/12/27
 */
@Slf4j
public class ChannelHandler {

    @Getter
    private SocketChannel channel;
    private Charset charset;

    public ChannelHandler(SocketChannel channel, Charset charset) {
        this.channel = channel;
        this.charset = charset;
    }

    public void writeAndFlush(Object msg) {
        try {
            byte[] bytes = msg.toString().getBytes(charset);
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        } catch (IOException e) {
            log.error("nio writeAndFlush error ", e);
        }
    }
}
