package cc.tianbin.demo.netty.rpc.protocol.serial;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class JavaSerializer implements ISerializer {

    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj); //序列化
            return bos.toByteArray();
        } catch (IOException e) {
            log.error("JavaSerializer serialize error ", e);
        }
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            return (T) ois.readObject();
        } catch (Exception e) {
            log.error("JavaSerializer deserialize error ", e);
        }
        return null;
    }

}
