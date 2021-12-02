package com.fx.client.rpc;

import com.fx.client.rpc.RpcClientHandler;
import com.fx.common.common.RpcConstants;
import com.fx.common.common.RpcRequest;
import com.fx.common.common.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class RpcClient {

    private static String HOST = "127.0.0.1";

    private static String bakDirPath = "/home/client";

    private EventLoopGroup workLoopGroup;

    private Bootstrap bootstrap;

    private ChannelFuture future;

    public RpcClient() {
        String systemType = System.getProperties().getProperty("os.name");
        systemType = systemType.toLowerCase();
        if (systemType.contains("windows")) {
            log.info("-------------当前系统为windows系统-------------");
            bakDirPath = "D:\\home\\client";
        }
    }


    @PostConstruct
    private Bootstrap initBootStrap() {
        workLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap
                .group(workLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline p = socketChannel.pipeline();
                        int maxObjectSize = 5 * 1024 * 1024;
//                            反序列化工具
                        p.addLast("objectDecoder", new ObjectDecoder(maxObjectSize, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
//                            序列化工具
                        p.addLast("objectEncoder", new ObjectEncoder());

                        p.addLast("rpcClientHandler", new RpcClientHandler());
                    }
                });
        return bootstrap;
    }

    public void connect() {
        try {
            future = bootstrap.connect(HOST, RpcConstants.PORT).sync();
            System.out.println(future.isSuccess());
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (!future.isSuccess()) {
                        log.warn("Failed to connect to server, try connect after {}s", 5);
                        channelFuture.channel().eventLoop().schedule(new Runnable() {
                            @Override
                            public void run() {
                                connect();
                            }
                        }, 5, TimeUnit.SECONDS);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RpcResponse pushRpcRequest(RpcRequest rpcRequest) {

        connect();
        Channel channel = future.channel();
        try {
            channel.writeAndFlush(rpcRequest);
//            同步阻塞 通过监听channel实现同步
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RpcClientHandler.response;
    }


}
