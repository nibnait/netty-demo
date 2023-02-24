package cc.tianbin.demo.netty.rpc.protocol.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by nibnait on 2023/02/23
 */
@Getter
@AllArgsConstructor
public enum SerialType {

    JSON_SERIAL((byte) 1),
    JAVA_SERIAL((byte) 2),
    ;

    private final Byte code;

}
