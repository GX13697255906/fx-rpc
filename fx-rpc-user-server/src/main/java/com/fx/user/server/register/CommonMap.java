package com.fx.user.server.register;

import com.fx.common.common.RpcResponse;
import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Administrator
 */
public class CommonMap {

    public static Map<String, Object> pathToBeanMap = Maps.newHashMap();

    public static Map<String, Method> pathToMethodMap = Maps.newHashMap();

}
