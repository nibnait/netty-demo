package cc.tianbin.demo.netty.rpc.protocol.netty;

import cc.tianbin.demo.netty.rpc.protocol.code.RpcDecode;
import cc.tianbin.demo.netty.rpc.protocol.code.RpcEncode;
import cc.tianbin.demo.netty.rpc.protocol.netty.handler.RpcServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {

    // 服务地址
    private String serverAddress;
    // 端口
    private int serverPort;

    public NettyServer(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void startNettyServer() {
        log.info("begin start netty server");
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().
                                addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                                        12,
                                        4,
                                        0,
                                        0))
                                .addLast(new RpcDecode())
                                .addLast(new RpcEncode())
                                .addLast(new RpcServerHandler());

                    }
                });
        try {
            ChannelFuture future = bootstrap.bind(this.serverAddress, this.serverPort).sync();
            log.info("netty server start success on Port, {}", this.serverPort);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("netty server start error ", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
