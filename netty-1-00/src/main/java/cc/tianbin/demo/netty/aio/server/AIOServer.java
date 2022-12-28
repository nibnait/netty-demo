package cc.tianbin.demo.netty.aio.server;

import cc.tianbin.demo.netty.CommonConstants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * Created by nibnait on 2022/12/27
 */
@Slf4j
public class AIOServer extends Thread {

    @Getter
    private AsynchronousServerSocketChannel serverSocketChannel;

    public static void main(String[] args) {
        new AIOServer().start();
    }

    @Override
    public void run() {
        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open(AsynchronousChannelGroup.withCachedThreadPool(Executors.newCachedThreadPool(), 10));
            serverSocketChannel.bind(new InetSocketAddress(CommonConstants.LOCAL_PORT));

            log.info("aio server start done.");

            // 等待
            CountDownLatch latch = new CountDownLatch(1);
            serverSocketChannel.accept(this, new AioServerChannelInitializer());
            latch.await();

        } catch (Exception e) {
            log.error("aio server start error ", e);
        }
    }
}
