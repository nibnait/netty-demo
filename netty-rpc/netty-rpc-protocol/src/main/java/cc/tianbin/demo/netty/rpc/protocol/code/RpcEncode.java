package cc.tianbin.demo.netty.rpc.protocol.code;

import cc.tianbin.demo.netty.rpc.protocol.core.Header;
import cc.tianbin.demo.netty.rpc.protocol.core.RpcProtocol;
import cc.tianbin.demo.netty.rpc.protocol.serial.ISerializer;
import cc.tianbin.demo.netty.rpc.protocol.serial.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RpcEncode extends MessageToByteEncoder<RpcProtocol<Object>> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcProtocol<Object> msg, ByteBuf out) throws Exception {

        Header header = msg.getHeader();

        out.writeShort(header.getMagic());
        out.writeByte(header.getSerialType());
        out.writeByte(header.getMessageType());
        out.writeLong(header.getRequestId());

        ISerializer serializer = SerializerFactory.getSerializer(header.getSerialType());
        // 序列化数据内容
        byte[] data = serializer.serialize(msg.getContent());
        // 数据内容的长度
        out.writeInt(data.length);
        // 写入数据
        out.writeBytes(data);

    }
}
