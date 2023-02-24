package cc.tianbin.demo.netty.rpc.consumer.controller;

import cc.tianbin.demo.netty.rpc.api.IUserService;
import cc.tianbin.demo.netty.rpc.protocol.annotation.RemoteReference;
import org.springframework.web.bind.annotation.*;

/**
 * Created by nibnait on 2023/02/23
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RemoteReference
    private IUserService iUserService;

    /**
     * http://127.0.0.1:8082/user/ping
     */
    @GetMapping("/ping")
    public String ping() {
        return "success";
    }

    /**
     * http://127.0.0.1:8082/user/queryById
     */
    @GetMapping("/queryById")
    public String queryById() {
        return iUserService.queryById(1);
    }

    /**
     * http://127.0.0.1:8082/user/queryById/1
     */
    @GetMapping("/queryById/{id}")
    public String queryById(@PathVariable Integer id) {
        return iUserService.queryById(id);
    }

}
