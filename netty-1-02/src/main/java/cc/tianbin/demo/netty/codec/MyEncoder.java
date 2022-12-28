package cc.tianbin.demo.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by nibnait on 2022/12/28
 */
public class MyEncoder extends MessageToByteEncoder {

    /**
     * 02开始 03结束
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object in, ByteBuf out) throws Exception {
        String msg = in.toString();
        byte[] bytes = msg.getBytes();

        byte[] send = new byte[bytes.length + 2];
        System.arraycopy(bytes, 0, send, 1, bytes.length);
        send[0] = 0x02;
        send[send.length - 1] = 0x03;

        out.writeInt(send.length);
        out.writeBytes(send);
    }
}
