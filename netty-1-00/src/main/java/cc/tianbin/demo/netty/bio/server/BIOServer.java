package cc.tianbin.demo.netty.bio.server;

import cc.tianbin.demo.netty.CommonConstants;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by nibnait on 2022/12/27
 */
@Slf4j
public class BIOServer extends Thread {

    private ServerSocket serverSocket = null;

    public static void main(String[] args) {
        BIOServer bioServer = new BIOServer();
        bioServer.start();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(CommonConstants.LOCAL_PORT));
            log.info("bio server start done.");

            while (true) {
                Socket socket = serverSocket.accept();
                BIOServerHandler handler = new BIOServerHandler(socket, Charset.forName(CommonConstants.CHAR_SET));
                handler.start();
            }

        } catch (IOException e) {
            log.error("bio server start error ", e);
        }
    }

}
