package cc.tianbin.demo.netty.bio.client;

import cc.tianbin.demo.netty.CommonConstants;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by nibnait on 2022/12/27
 */
@Slf4j
public class BIOClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(CommonConstants.LOCAL_IP, CommonConstants.LOCAL_PORT);
            log.info("bio client start done.");
            BIOClientHandler handler = new BIOClientHandler(socket, Charset.forName(CommonConstants.CHAR_SET));
            handler.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
