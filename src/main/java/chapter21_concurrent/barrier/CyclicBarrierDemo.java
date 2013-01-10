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
package chapter21_concurrent.barrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-1-10
 */
class CyclicBarrierDemo {
    public static void main(String[] args) {
        for (int i = 1; i <= 15000; i++) {
            System.out.println("****************分割线，这是第${i}次测试*****************");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    testCyclic();
                }
            }).start();
        }
    }

    private static void testCyclic() {
        long startTime = System.currentTimeMillis();
        int threadCount = 6;
        //障栅集合点(同步器)
        final CyclicBarrier barrier = new CyclicBarrier(threadCount + 1);
        ExecutorService executorService = Executors.newCachedThreadPool(new NamedThreadPoolFactory("序列化测试-"));
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        exec();
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
                    execException();
                    try {
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    System.out.println("=============error=========${e}");
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
        long endTime = System.currentTimeMillis();
        System.out.println("---Whole time is : " + (endTime - startTime) / 1000 + "秒-----");
        System.out.println("--------------END ALL--------------");
    }


    private static void exec() throws Exception {
        work();
    }

    private static void execException() throws Exception {
        work();
        throw new RuntimeException("^^^^^^^^^My RuntimeException ^^^^^^^^");
    }

    private static void work() throws Exception {
        System.out.println("-------START-----当前线程id：${Thread.currentThread().id}，线程Name：${Thread.currentThread().name}");
        System.out.println("---------开始休息100毫秒---------");
        Thread.sleep(100L);
        System.out.println("-------END------当前线程id：${Thread.currentThread().id}，线程Name：${Thread.currentThread().name}");
    }

}
