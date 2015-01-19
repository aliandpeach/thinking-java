/*
 * Created on 13-1-10
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2013 the original author or authors.
 */
package ch21concurrent.barrier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-1-10
 */
class CyclicBarrierDemo {
    private static final Logger log = LoggerFactory.getLogger(CyclicBarrierDemo.class);

    /**
     * 测试结果：
     * 运行3000次
     * CyclicBarrier栅栏         --> 15秒
     * CompletionService线程池    --> 8秒
     *
     * @param args
     */
    public static void main(String[] args) {
//        testCyclic();
        testFutureThreadPool(3);
//        testThreadPool();
    }

    /**
     * 测试CompletionService线程池
     *
     * @param runtimes
     */
    private static void testFutureThreadPool(int runtimes) {
        log.info("------------开始测试了-------------");
        for (int i = 1; i <= runtimes; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    execFutureThreadPoolTask(600);
                }
            }).start();
        }
    }

    /**
     * 测试CompletionService线程池
     */
    private static void testThreadPool() {
        log.info("------------开始测试了-------------");
        for (int i = 1; i <= 3000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    execThreadPoolTask();
                }
            }).start();
        }
    }

    /**
     * 测试CyclicBarrier栅栏
     */
    private static void testCyclic() {
        log.info("------------开始测试了-------------");
        for (int i = 1; i <= 3000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    execCyclicTask();
                }
            }).start();
        }
    }

    private static void execFutureThreadPoolTask(int taskNum) {
        ExecutorService threadPool = null;
        try {
            threadPool = Executors.newFixedThreadPool(taskNum);
            List<Future<String>> futures = new ArrayList<Future<String>>(10);
//            CompletionService<String> pool = new ExecutorCompletionService<String>(threadPool);

            for (int i = 0; i < taskNum - 1; i++) {
                final long expendTime = 1000L;
                futures.add(threadPool.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        exec(expendTime);
                        return "success";
                    }
                }));
            }
            futures.add(threadPool.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    execException(2000L);
                    return "success";
                }
            }));

            for (Future<String> future : futures) {
                String result = future.get();
            }
            log.info("-------最后这个单个整体的任务结束了-------");
        } catch (Exception e) {
            log.error("-------取结果错误------");
        } finally {
            if (threadPool != null) {
                threadPool.shutdown();
            }
        }
    }

    private static void execThreadPoolTask() {
        ExecutorService threadPool = null;
        try {
            int taskNum = 6;
            threadPool = Executors.newFixedThreadPool(taskNum);
            CompletionService<String> pool = new ExecutorCompletionService<String>(threadPool);

            for (int i = 0; i < taskNum - 1; i++) {
                final long expendTime = (i + 1) * 1000L;
                pool.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        exec(expendTime);
                        return "success";
                    }
                });
            }
            pool.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    execException(2000L);
                    return "success";
                }
            });

            for (int i = 0; i < taskNum; i++) {
                String result = pool.take().get();
//                log.info("----最小单元功能返回结果：" + result);
            }
            log.info("-------最后这个单个整体的任务结束了-------");
        } catch (Exception e) {
            log.error("-------取结果错误------");
        } finally {
            if (threadPool != null) {
                threadPool.shutdown();
            }
        }
    }

    private static void execCyclicTask() {
//        long startTime = System.currentTimeMillis();
        int threadCount = 6;
        //障栅集合点(同步器)
        final CyclicBarrier barrier = new CyclicBarrier(threadCount + 1);
        ExecutorService executorService = Executors.newCachedThreadPool(new NamedThreadPoolFactory("序列化测试---"));
        for (int i = 0; i < 5; i++) {
            final long expendTime = (i + 1) * 1000L;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        exec(expendTime);
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    execException(2000L);
                    try {
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    log.error("=============error=========${e}");
                    e.printStackTrace();
                }
            }
        });
        try {
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        log.info("--------每次的栅栏测试结束了---------");
//        long endTime = System.currentTimeMillis();
//        log.info("---Whole time is : " + (endTime - startTime) / 1000 + "秒-----");
//        log.info("--------------END ALL--------------");
    }


    private static void exec(long expendTime) throws Exception {
        work(expendTime);
    }

    private static void execException(long expendTime) throws Exception {
        work(expendTime);
//        throw new RuntimeException("^^^^^^^^^My RuntimeException ^^^^^^^^");
    }

    private static void work(long expendTime) throws Exception {
//        log.info("-------START-----当前线程id：${Thread.currentThread().id}，线程Name：${Thread.currentThread().name}");
//        log.info("---------开始休息100毫秒---------");
        Thread.sleep(expendTime);
//        log.info("-------END------当前线程id：${Thread.currentThread().id}，线程Name：${Thread.currentThread().name}");
    }

}
