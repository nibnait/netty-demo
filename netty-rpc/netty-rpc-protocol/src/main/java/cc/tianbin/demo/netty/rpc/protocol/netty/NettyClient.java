package cc.tianbin.demo.netty.rpc.protocol.netty;

import cc.tianbin.demo.netty.rpc.protocol.code.RpcDecode;
import cc.tianbin.demo.netty.rpc.protocol.code.RpcEncode;
import cc.tianbin.demo.netty.rpc.protocol.core.RpcProtocol;
import cc.tianbin.demo.netty.rpc.protocol.core.RpcRequest;
import cc.tianbin.demo.netty.rpc.protocol.netty.handler.RpcClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyClient {

    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private String serviceAddress;
    private int servicePort;

    public NettyClient(String host, int port) {

        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {


                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new RpcDecode())
                                .addLast(new RpcEncode())
                                .addLast(new RpcClientHandler());
                    }
                });
        this.serviceAddress = host;
        this.servicePort = port;
    }


    public void sendRequest(RpcProtocol<RpcRequest> protocol) throws Exception {
        final ChannelFuture future = bootstrap.connect(this.serviceAddress, this.servicePort).sync();
        // 注册一个监听器，如果出问题就关闭group
        future.addListener(listener -> {
            if (future.isSuccess()) {
                log.info("connect rpc server {} success.", this.serviceAddress);
            } else {
                log.error("connect rpc server {} failed. ", this.servicePort);
                future.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        log.info("begin transfer data");
        // 向服务端写数据
        future.channel().writeAndFlush(protocol);
    }
}
