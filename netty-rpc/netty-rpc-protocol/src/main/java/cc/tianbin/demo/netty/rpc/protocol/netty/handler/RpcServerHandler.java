package cc.tianbin.demo.netty.rpc.protocol.netty.handler;

import cc.tianbin.demo.netty.rpc.protocol.core.Header;
import cc.tianbin.demo.netty.rpc.protocol.core.RpcProtocol;
import cc.tianbin.demo.netty.rpc.protocol.core.RpcRequest;
import cc.tianbin.demo.netty.rpc.protocol.core.RpcResponse;
import cc.tianbin.demo.netty.rpc.protocol.core.enums.MessageType;
import cc.tianbin.demo.netty.rpc.protocol.spring.service.Mediator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcServerHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcRequest> msg) throws Exception {

        RpcProtocol<RpcResponse> resProtocol = new RpcProtocol<>();
        Header header = msg.getHeader();
        header.setMessageType(MessageType.RESPONSE.getCode());
        // 这里就是调用 server 方法的地方
        Object result = Mediator.getInstance().processor(msg.getContent());
        resProtocol.setHeader(header);
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setMsg("success");
        rpcResponse.setData(result);
        resProtocol.setContent(rpcResponse);
        ctx.writeAndFlush(resProtocol);
    }
}
