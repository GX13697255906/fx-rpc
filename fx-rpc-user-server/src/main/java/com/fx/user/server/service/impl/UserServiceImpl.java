package com.fx.user.server.service.impl;

import com.fx.common.annoation.RpcService;
import com.fx.user.server.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    @RpcService(path = "/getName")
    public String getName() {
        return "xun.guo";
    }
}
