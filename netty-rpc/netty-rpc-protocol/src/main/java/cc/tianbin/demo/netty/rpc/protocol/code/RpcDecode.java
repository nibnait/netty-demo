package cc.tianbin.demo.netty.rpc.protocol.code;

import cc.tianbin.demo.netty.rpc.protocol.constant.RpcConstant;
import cc.tianbin.demo.netty.rpc.protocol.core.Header;
import cc.tianbin.demo.netty.rpc.protocol.core.RpcProtocol;
import cc.tianbin.demo.netty.rpc.protocol.core.RpcRequest;
import cc.tianbin.demo.netty.rpc.protocol.core.RpcResponse;
import cc.tianbin.demo.netty.rpc.protocol.core.enums.MessageType;
import cc.tianbin.demo.netty.rpc.protocol.serial.ISerializer;
import cc.tianbin.demo.netty.rpc.protocol.serial.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class RpcDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // 如果比头的长度都小，是不是就没有内容
        if (in.readableBytes() < RpcConstant.HEAD_TOTAL_LEN) {
            return;
        }
        in.markReaderIndex();
        short magic = in.readShort();

        if (magic != RpcConstant.MAGIC) {
            throw new IllegalArgumentException(String.format("magic %s", magic));
        }

        byte serialType = in.readByte();
        byte messageType = in.readByte();
        long requestId = in.readLong();
        int dataLength = in.readInt();
        // 如果剩余读取的字节小于dataLength
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }

        byte[] content = new byte[dataLength];
        in.readBytes(content);

        Header header = new Header(magic, serialType, messageType, requestId, dataLength);
        ISerializer serializer = SerializerFactory.getSerializer(serialType);
        MessageType msgType = MessageType.getByCode(messageType);

        switch (msgType) {
            case REQUEST:
                // 反序列化
                RpcRequest rpcRequest = serializer.deserialize(content, RpcRequest.class);
                // 封装成RpcProtocol对象
                RpcProtocol<RpcRequest> rpcProtocol = new RpcProtocol<>();
                rpcProtocol.setHeader(header);
                rpcProtocol.setContent(rpcRequest);
                out.add(rpcProtocol);
                break;
            case RESPONSE:
                // 反序列化
                RpcResponse rpcResponse = serializer.deserialize(content, RpcResponse.class);
                // 封装成RpcProtocol对象
                RpcProtocol<RpcResponse> protocol = new RpcProtocol<>();
                protocol.setHeader(header);
                protocol.setContent(rpcResponse);
                out.add(protocol);
                break;
            default:
                break;
        }
    }
}
