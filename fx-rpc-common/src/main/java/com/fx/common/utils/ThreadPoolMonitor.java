package com.fx.common.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author guoxun
 * 线程池监听工具
 */
//方法	含义
//        getActiveCount()	线程池中正在执行任务的线程数量
//        getCompletedTaskCount()	线程池已完成的任务数量，该值小于等于taskCount
//        getCorePoolSize()	线程池的核心线程数量
//        getLargestPoolSize()	线程池曾经创建过的最大线程数量。通过这个数据可以知道线程池是否满过，也就是达到了maximumPoolSize
//        getMaximumPoolSize()	线程池的最大线程数量
//        getPoolSize()	线程池当前的线程数量
//        getTaskCount()	线程池已经执行的和未执行的任务总数
//        队列里缓存的任务数量
@Slf4j
@Data
public class ThreadPoolMonitor implements Serializable {

    private static ThreadPoolExecutor staticThreadPoolExecutor = null;

    public ThreadPoolMonitor(ThreadPoolExecutor threadPoolExecutor) {
        staticThreadPoolExecutor = threadPoolExecutor;
    }

    //线程池中正在执行任务的线程数量
    private int activeCount;
    //    线程池已完成的任务数量，该值小于等于taskCount
    private Long completedTaskCount;
    //    线程池的核心线程数量
    private int corePoolSize;
    //    线程池曾经创建过的最大线程数量。通过这个数据可以知道线程池是否满过，也就是达到了maximumPoolSize
    private int largestPoolSize;
    //    线程池的最大线程数量
    private int maximumPoolSize;
    //    线程池当前的线程数量
    private int poolSize;
    //    线程池已经执行的和未执行的任务总数
    private Long taskCount;
    //    队列里缓存的任务数量
    private int queueSize;

    public ThreadPoolMonitor(int activeCount, Long completedTaskCount, int corePoolSize, int largestPoolSize, int maximumPoolSize, int poolSize, Long taskCount, int queueSize) {
        this.activeCount = activeCount;
        this.completedTaskCount = completedTaskCount;
        this.corePoolSize = corePoolSize;
        this.largestPoolSize = largestPoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.poolSize = poolSize;
        this.taskCount = taskCount;
        this.queueSize = queueSize;
    }

    public ThreadPoolMonitor getMonitor() {
        return new ThreadPoolMonitor(
                staticThreadPoolExecutor.getActiveCount(),
                staticThreadPoolExecutor.getCompletedTaskCount(),
                staticThreadPoolExecutor.getCorePoolSize(),
                staticThreadPoolExecutor.getLargestPoolSize(),
                staticThreadPoolExecutor.getMaximumPoolSize(),
                staticThreadPoolExecutor.getPoolSize(),
                staticThreadPoolExecutor.getTaskCount(),
                staticThreadPoolExecutor.getQueue().size());
    }

}
