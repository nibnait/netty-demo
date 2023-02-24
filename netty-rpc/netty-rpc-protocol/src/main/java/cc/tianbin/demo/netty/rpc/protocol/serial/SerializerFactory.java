package cc.tianbin.demo.netty.rpc.protocol.serial;

import cc.tianbin.demo.netty.rpc.protocol.core.enums.SerialType;

import java.util.concurrent.ConcurrentHashMap;

public class SerializerFactory {

    private final static ConcurrentHashMap<Byte, ISerializer> serializerMap = new ConcurrentHashMap();

    static {
        serializerMap.put(SerialType.JSON_SERIAL.getCode(), new JsonSerializer());
        serializerMap.put(SerialType.JAVA_SERIAL.getCode(), new JavaSerializer());
    }

    public static ISerializer getSerializer(Byte key) {
        ISerializer iSerializer = serializerMap.get(key);
        if (iSerializer == null) {
            return new JavaSerializer();
        }
        return iSerializer;

    }
}
