package cc.tianbin.demo.netty.rpc.protocol.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 调用方需要生成远程调用代理的注解
 * Created by nibnait on 2023/02/23
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Autowired
public @interface RemoteReference {
}
