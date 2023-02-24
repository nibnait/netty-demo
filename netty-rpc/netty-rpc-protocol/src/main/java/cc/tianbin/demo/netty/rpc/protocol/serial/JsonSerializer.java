package cc.tianbin.demo.netty.rpc.protocol.serial;

import com.alibaba.fastjson.JSON;

public class JsonSerializer implements ISerializer {

    @Override
    public <T> byte[] serialize(T obj) {
        return JSON.toJSONString(obj).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return JSON.parseObject(new String(data), clazz);
    }

}
