package cc.tianbin.demo.netty.rpc.protocol.core;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcResponse implements Serializable {

    private Object data;
    private String msg;
}
