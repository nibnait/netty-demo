package cc.tianbin.demo.netty.codec;

import cc.tianbin.demo.netty.CommonConstants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 自定义解码器
 * Created by nibnait on 2022/12/28
 */
public class MyDecoder extends ByteToMessageDecoder {

    /**
     * 02开始 03结束
     */

    // 数据包基础长度
    private final int BASE_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() < BASE_LENGTH) {
            // 长度不足4，
            return;
        }

        // 记录包头位置
        int beginIndex;

        while (true) {
            // 获取包头开始的index
            beginIndex = in.readerIndex();
            // 标记包头开始的index
            in.markReaderIndex();
            // 督导了协议的开始标志，结束 while 循环
            if (in.readByte() == 0x02) {
                break;
            }

            // 未读到包头，略过一个字节
            // 每次略过一个字节，去读取包头信息的开始标记
            in.resetReaderIndex();
            in.readByte();
            // 当略过一个字节之后，数据包的长度有变得不满足，
            // 此时应该结束，等待后面的数据到达
            if (in.readableBytes() < BASE_LENGTH) {
                return;
            }
        }

        // 剩余长度不足可读取熟了（没有内容长度位）
        int readableCount = in.readableBytes();
        if (readableCount <= 1) {
            in.readerIndex(beginIndex);
            return;
        }

        // 长度域占1个字节，读取 int
        ByteBuf byteBuf = in.readBytes(1);
        String msgLengthStr = byteBuf.toString(CommonConstants.CHAR_SET_UTF8);
        int msgLength = Integer.parseInt(msgLengthStr);

        // 剩余长度不足可读取熟了（没有结尾标识）
        readableCount = in.readableBytes();
        if (readableCount < msgLength + 1) {
            in.readerIndex(beginIndex);
            return;
        }

        ByteBuf msgContent = in.readBytes(msgLength);

        // 如果没有结尾标识，还原指针位置
        byte end = in.readByte();
        if (end != 0x03) {
            in.readerIndex(beginIndex);
            return;
        }

        out.add(msgContent.toString(CommonConstants.CHAR_SET_UTF8));
    }
}
