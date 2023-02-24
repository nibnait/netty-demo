package cc.tianbin.demo.netty.rpc.protocol.core;

import lombok.Data;

import java.io.Serializable;

@Data
public class Header implements Serializable {

    private short magic;// 魔数  2个字节
    private byte serialType;// 序列化类型
    private byte messageType;// 请求类型
    private long requestId;// 请求id  8个字节
    private int length;// 消息体长度 4个字节

    public Header() {
    }

    public Header(short magic, byte serialType, byte messageType, long requestId, int length) {
        this.magic = magic;
        this.serialType = serialType;
        this.messageType = messageType;
        this.requestId = requestId;
        this.length = length;
    }
}
