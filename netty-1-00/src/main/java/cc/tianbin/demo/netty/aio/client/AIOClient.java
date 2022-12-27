package cc.tianbin.demo.netty.aio.client;

import cc.tianbin.demo.netty.CommonConstants;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.Future;

/**
 * Created by nibnait on 2022/12/27
 */
@Slf4j
public class AIOClient {

    public static void main(String[] args) throws Exception {
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();

        Future<Void> future = socketChannel.connect(new InetSocketAddress(CommonConstants.LOCAL_IP, CommonConstants.LOCAL_PORT));

        log.info("aio client start done.");
        future.get();
        socketChannel.read(ByteBuffer.allocate(1024), null, new AIOClientHandler(socketChannel, Charset.forName(CommonConstants.CHAR_SET)));

        Thread.sleep(100000);
    }
}
