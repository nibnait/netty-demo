package cc.tianbin.demo.netty.nio.server;

import cc.tianbin.demo.netty.CommonConstants;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.Charset;

/**
 * Created by nibnait on 2022/12/27
 */
@Slf4j
public class NIOServer {

    private Selector selector;
    private ServerSocketChannel socketChannel;

    public static void main(String[] args) {
        new NIOServer().bind(CommonConstants.LOCAL_PORT);
    }

    public void bind(int port) {
        try {
            selector = Selector.open();
            socketChannel = ServerSocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.socket().bind(new InetSocketAddress(port), 1024);
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);

            log.info("nio server start done.");
            new NIOServerHandler(selector, Charset.forName(CommonConstants.CHAR_SET)).start();
        } catch (Exception e) {
            log.error("NIOServer error ", e);
        }
    }

}
