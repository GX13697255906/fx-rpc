package com.fx.client.rpc;

import com.alibaba.fastjson.JSONObject;
import com.fx.common.common.RpcRequest;
import com.fx.common.common.RpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 */
@Slf4j
@ChannelHandler.Sharable
public class RpcClientHandler extends ChannelInboundHandlerAdapter {


    public static RpcResponse response;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        response = (RpcResponse) msg;
        log.info("client response = {}", JSONObject.toJSONString(response));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("exceptionCaught:--------------- {} ---------------", cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}
