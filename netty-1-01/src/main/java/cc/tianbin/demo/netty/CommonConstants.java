package cc.tianbin.demo.netty;

import io.github.nibnait.common.utils.date.DateTimeConvertUtils;
import io.github.nibnait.common.utils.date.DateUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by nibnait on 2022/12/27
 */
public class CommonConstants {

    public static final String LOCAL_IP = "127.0.0.1";
    public static final Integer LOCAL_PORT = 7397;
    public static final String CHAR_SET = "utf-8";
    public static final Charset CHAR_SET_UTF8 = StandardCharsets.UTF_8;

    public static String CURRENT_TIME() {
        return DateTimeConvertUtils.timeStamp2DateTimeString(DateUtils.currentTimeMillis());
    }
}
