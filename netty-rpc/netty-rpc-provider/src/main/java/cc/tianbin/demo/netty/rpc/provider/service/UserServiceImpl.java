package cc.tianbin.demo.netty.rpc.provider.service;

import cc.tianbin.demo.netty.rpc.api.IUserService;
import cc.tianbin.demo.netty.rpc.protocol.annotation.RemoteService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nibnait on 2023/02/23
 */
@Slf4j
@RemoteService
public class UserServiceImpl implements IUserService {

    private static final Map<Integer, String> user = new HashMap<>();

    static {
        user.put(1, "1: tom");
        user.put(2, "2: mike");
    }

    @Override
    public String queryById(Integer id) {
        log.info("queryById {}", id);
        String userInfo = user.get(id);
        return userInfo == null ? "用户不存在" : userInfo;
    }
}
