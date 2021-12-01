package com.fx.user.server.rpc;

import com.fx.common.common.RpcConstants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class RpcServer {


    private static String bakDirPath = "/home/server";

    public RpcServer() {
        String systemType = System.getProperties().getProperty("os.name");
        systemType = systemType.toLowerCase();
        if (systemType.contains("windows")) {
            log.info("-------------当前系统为windows系统-------------");
            bakDirPath = "D:\\home\\server";
        }
    }

    private EventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    private ServerBootstrap bootstrap;

    private ChannelFuture channelFuture;

    public void initServer() {

        bossGroup = new NioEventLoopGroup();
        workGroup = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline p = socketChannel.pipeline();
                        int maxObjectSize = 5 * 1024 * 1024;
//                            反序列化工具
                        p.addLast("objectDecoder", new ObjectDecoder(maxObjectSize, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
//                            序列化工具
                        p.addLast("objectEncoder", new ObjectEncoder());

                        p.addLast("rpcServerHandler", new RpcServerHandler());

                    }
                });
        try {
            channelFuture = bootstrap.bind(RpcConstants.PORT).sync();
//            // 监听服务端关闭，并阻塞等待
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            bossGroup.shutdownGracefully();
//            workGroup.shutdownGracefully();
        }
    }

    @PreDestroy
    public void shutDown() {
        try {
            // 监听服务端关闭，并阻塞等待
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭两个 EventLoopGroup 对象
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
