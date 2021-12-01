package com.fx.common.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Data
public class RpcRequest implements Serializable {

    private String requestId;

    private String path;

    private HashMap<String, Object> parameters;

}
