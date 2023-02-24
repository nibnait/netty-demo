package cc.tianbin.demo.netty.rpc.protocol.netty.handler;

import cc.tianbin.demo.netty.rpc.protocol.core.RequestHolder;
import cc.tianbin.demo.netty.rpc.protocol.core.RpcFuture;
import cc.tianbin.demo.netty.rpc.protocol.core.RpcProtocol;
import cc.tianbin.demo.netty.rpc.protocol.core.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcResponse> msg) throws Exception {
        log.info("receive rpc server result");
        long requestId = msg.getHeader().getRequestId();

        // 删除映射关系
        RpcFuture<RpcResponse> future = RequestHolder.REQUEST_MAP.remove(requestId);

        // 我们之前说异步等待服务端发送数据过来，那么只要服务端发送数据过来，就会调用管道 RpcClientHandler 的 read() 方法
        // 那么当初 future.getPromise().get() 如果不再阻塞获取数据呢？就是通过给 Promise 中的 Success 设置值，同时会唤醒阻塞的线程
        // 一旦唤醒线程，future.getPromise().get() 就会不再阻塞，就获取到服务端返回的数据

        // setSuccess(msg.getContent()) 通过这个方法唤醒 get() 方法获取到服务端返回的数据
        // 数据就是通过 setSuccess 方法设置的数据 msg.getContent()
        future.getPromise().setSuccess(msg.getContent());

    }
}
