package cc.tianbin.demo.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * Created by nibnait on 2022/12/28
 */
public class MyOutMsgHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void read(ChannelHandlerContext context) throws Exception {
        context.writeAndFlush("ChannelOutboundHandlerAdapter.read 发来一条消息 \n");
        super.read(context);
    }

    @Override
    public void write(ChannelHandlerContext context, Object msg, ChannelPromise promise) throws Exception {
        context.writeAndFlush("ChannelOutboundHandlerAdapter.write 发来一条消息 \n");
        super.write(context, msg, promise);
    }

}
