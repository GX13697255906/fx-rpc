package com.fx.client.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.fx.client.rpc.RpcClient;
import com.fx.common.common.RpcRequest;
import com.fx.common.common.RpcResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Administrator
 */
@RestController
public class ClientController {

    @Autowired
    private RpcClient rpcClient;

    @Autowired
    private HttpServletRequest request;

    @PostMapping(value = "/**")
    public RpcResponse getResponse(@RequestBody HashMap<String, Object> parameters) {
        RpcRequest rpcRequest = new RpcRequest();
        String requestPath = request.getRequestURI();
        rpcRequest.setPath(requestPath);
        rpcRequest.setRequestId("REQUEST_ID-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN) + "-" + requestPath);
        rpcRequest.setParameters(parameters);
        RpcResponse rpcResponse = rpcClient.pushRpcRequest(rpcRequest);
        return rpcResponse;
    }

}
