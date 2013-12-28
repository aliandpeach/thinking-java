/*
 * Created on 12-11-15
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
 * Copyright @2012 the original author or authors.
 */
package chapter01_basic;

import java.util.Date;
import java.util.TimerTask;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 12-11-15
 */
public class Timer {
    public static void main(String[] args) {
        java.util.Timer timer = new java.util.Timer(true);
        // true 说明这个timer以daemon方式运行（优先级低，
        // 程序结束timer也自动结束），注意，javax.swing
        // 包中也有一个Timer类，如果import中用到swing包，
        // 要注意名字的冲突。

        TimerTask task = new TimerTask() {
            public void run() {
                //每次需要执行的代码放到这里面。
            }
        };
        //以下是几种调度task的方法：
        timer.schedule(task, 1000L);
        // time为Date类型：在指定时间执行一次。

        timer.schedule(task, new Date(), 1000L);
        // firstTime为Date类型,period为long
        // 从firstTime时刻开始，每隔period毫秒执行一次。

        timer.schedule(task, 1000L);
        // delay 为long类型：从现在起过delay毫秒执行一次

        timer.schedule(task, 1000L, 2000L);
        // delay为long,period为long：从现在起过delay毫秒以后，每隔period毫秒执行一次。
    }
}
