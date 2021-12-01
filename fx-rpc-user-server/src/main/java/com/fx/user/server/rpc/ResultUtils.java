package com.fx.user.server.rpc;

import com.fx.common.common.RpcRequest;
import com.fx.common.common.RpcResponse;
import com.fx.user.server.register.CommonMap;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Administrator
 */
public class ResultUtils {

    public static RpcResponse getRpcResponse(RpcRequest rpcRequest) throws InvocationTargetException {
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());
        String path = rpcRequest.getPath();
        Object object = CommonMap.pathToBeanMap.get(path);
        Method method = CommonMap.pathToMethodMap.get(path);
        FastClass fastClass = FastClass.create(object.getClass());
        FastMethod fastMethod = fastClass.getMethod(method);
        Object result = fastMethod.invoke(object, null);
        rpcResponse.setResult(result);
        return rpcResponse;
    }

}
