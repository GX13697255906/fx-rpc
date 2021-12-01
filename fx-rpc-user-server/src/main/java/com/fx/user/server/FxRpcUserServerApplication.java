package com.fx.user.server;

import com.fx.common.utils.ThreadPoolExecutorUtils;
import com.fx.user.server.rpc.RpcServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Administrator
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, RedisAutoConfiguration.class})
public class FxRpcUserServerApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(FxRpcUserServerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ThreadPoolExecutorUtils.getThreadPoolExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    RpcServer rpcServer = new RpcServer();
                    rpcServer.initServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
