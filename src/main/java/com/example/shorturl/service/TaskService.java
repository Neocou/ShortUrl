package com.example.shorturl.service;

import com.example.shorturl.common.Slog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaskService {
    @Autowired
    private Slog slog;
    private static int MAX_THREADS;

    @Value("${spiderdt.task-thread-nums}")
    public void setMaxThreads(int num) {
        MAX_THREADS = num;
    }
    private static final ScheduledExecutorService workerPool = Executors.newScheduledThreadPool(MAX_THREADS, new WorkThreadFactory());

    public static Future<?>  submit(Callable<?> callable) {
        return workerPool.schedule(callable, 0, TimeUnit.NANOSECONDS);
    }

    @PostConstruct
    private void init() {
        slog.info("TaskService init...");
        // 初始化阶段化的定时任务
    }

    @PreDestroy
    private void destroy() {
        slog.info("TaskService destroying...");
        workerPool.shutdownNow();
    }
    private static class WorkThreadFactory implements ThreadFactory {
        private final AtomicInteger num = new AtomicInteger(0);
        private final String THREAD_NAME_PREFIX = "task-worker-";

        @Override
        public Thread newThread(Runnable r) {
            String threadName = THREAD_NAME_PREFIX + num.getAndIncrement();
            Thread thread = new Thread(r, threadName);
            thread.setPriority(Thread.MAX_PRIORITY);
            return thread;
        }

    }
}
