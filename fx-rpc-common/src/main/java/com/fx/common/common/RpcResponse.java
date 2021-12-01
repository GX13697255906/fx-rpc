package com.fx.common.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class RpcResponse implements Serializable {

    private String requestId;

    private Object result;


}
