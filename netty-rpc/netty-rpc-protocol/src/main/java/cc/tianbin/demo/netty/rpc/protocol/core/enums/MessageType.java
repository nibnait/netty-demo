package cc.tianbin.demo.netty.rpc.protocol.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by nibnait on 2023/02/23
 */
@Getter
@AllArgsConstructor
public enum MessageType {

    REQUEST((byte) 1),
    RESPONSE((byte) 2),
    HEART_BEAT((byte) 3),
    ;

    private final Byte code;

    public static MessageType getByCode(Byte code) {
        if (code == null) {
            return null;
        }
        for (MessageType value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("%d 非法的请求类型", code));
    }

}
