package cc.tianbin.demo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by nibnait on 2022/12/29
 */
@Slf4j
public class ApiTest {

    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    // 基于换行符号
                    channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    // 解码转String，注意调整自己的编码格式GBK、UTF-8
                    channel.pipeline().addLast(new StringDecoder(CommonConstants.CHAR_SET_UTF8));
                    // 解码转String，注意调整自己的编码格式GBK、UTF-8
                    channel.pipeline().addLast(new StringEncoder(CommonConstants.CHAR_SET_UTF8));
                    // 在管道中添加我们自己的接收数据实现方法
                    channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            //接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
                            log.info("{} 客户端收到消息: {}", CommonConstants.CURRENT_TIME(), msg);
                        }
                    });
                }
            });
            ChannelFuture f = b.connect("127.0.0.1", 7397).sync();
            System.out.println("client start done.");

            // 向服务端发送信息
            f.channel().writeAndFlush("你好，SpringBoot启动的netty服务端，“我的结尾是一个换行符，用于传输半包粘包处理”\r\n");

            f.channel().closeFuture().syncUninterruptibly();
        } catch (Exception e) {
            log.error("", e);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
