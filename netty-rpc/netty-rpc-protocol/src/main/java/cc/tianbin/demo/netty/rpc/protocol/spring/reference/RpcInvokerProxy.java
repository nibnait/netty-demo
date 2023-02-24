package cc.tianbin.demo.netty.rpc.protocol.spring.reference;

import cc.tianbin.demo.netty.rpc.protocol.constant.RpcConstant;
import cc.tianbin.demo.netty.rpc.protocol.core.*;
import cc.tianbin.demo.netty.rpc.protocol.core.enums.MessageType;
import cc.tianbin.demo.netty.rpc.protocol.core.enums.SerialType;
import cc.tianbin.demo.netty.rpc.protocol.netty.NettyClient;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class RpcInvokerProxy implements InvocationHandler {

    private String host;

    private int port;

    public RpcInvokerProxy(String serviceAddress, int servicePort) {
        this.host = serviceAddress;
        this.port = servicePort;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 建立连接，并且发送报文
        RpcProtocol<RpcRequest> rpcProtocol = new RpcProtocol<>();

        long requestId = RequestHolder.REQUEST_ID.incrementAndGet();
        Header header = new Header(RpcConstant.MAGIC, SerialType.JSON_SERIAL.getCode(), MessageType.REQUEST.getCode(), requestId, 0);
        rpcProtocol.setHeader(header);

        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParams(args);
        rpcRequest.setParameterTypes(method.getParameterTypes());
        rpcProtocol.setContent(rpcRequest);

        // 发送数据到服务端
        NettyClient nettyClient = new NettyClient(host, port);
        RpcFuture<RpcResponse> future = new RpcFuture<>(new DefaultPromise<RpcResponse>(new DefaultEventLoop()));
        RequestHolder.REQUEST_MAP.put(requestId, future);
        nettyClient.sendRequest(rpcProtocol);

        // 获取返回结果
        return future.getPromise().get().getData();
    }
}
