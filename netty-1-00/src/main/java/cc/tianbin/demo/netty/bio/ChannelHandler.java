package cc.tianbin.demo.netty.bio;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by nibnait on 2022/12/27
 */
@Slf4j
public class ChannelHandler {

    @Getter
    private Socket socket;
    private Charset charset;

    public ChannelHandler(Socket socket, Charset charset) {
        this.socket = socket;
        this.charset = charset;
    }

    public void writeAndFlush(Object msg) {
        OutputStream out = null;
        try {
            out = socket.getOutputStream();
            out.write((msg.toString()).getBytes(charset));
            out.flush();
        } catch (IOException e) {
            log.error("bio writeAndFlush error ", e);
        }
    }

}
