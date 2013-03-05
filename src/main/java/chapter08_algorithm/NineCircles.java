/*
 * Created on 13-3-4
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
package chapter08_algorithm;

/**
 * 九连环
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-4
 */
public class NineCircles {
    /**
     * 上环一个
     *
     * @param status
     * @param index
     */
    private static void up(int[] status, int index) {
        if (status[index - 1] == 0) {
            System.out.println("----上环：" + index);
            upCount++;
            status[index - 1] = 1;
        }
    }

    /**
     * 下环一个
     *
     * @param status
     * @param index
     */
    private static void down(int[] status, int index) {
        if (status[index - 1] == 1) {
            System.out.println("----下环：" + index);
            downCount++;
            status[index - 1] = 0;
        }
    }

    /**
     * 上环number个
     *
     * @param status
     * @param number
     */
    private static void UP(int[] status, int number) {
        if (number == 1) {
            up(status, 1);
        } else if (number == 2) {
            up(status, 1);
            up(status, 2);
        } else if (number > 2) {
            DOWN(status, number - 2);
            up(status, number - 1);
            up(status, number);
            UP(status, number - 1);
        }
    }

    /**
     * 下环number个
     *
     * @param status
     * @param number
     */
    private static void DOWN(int[] status, int number) {
        if (number == 1) {
            down(status, number);
        } else if (number == 2) {
            up(status, 1);
            down(status, 2);
            down(status, 1);
        } else if (number > 2) {
            DOWN(status, number - 2);
            up(status, number - 1);
            down(status, number);
            DOWN(status, number - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println("----9个环上环----START");
        UP(STATUS_OFF, STATUS_OFF.length);
        System.out.println("上环总步骤数：" + upCount);
        System.out.println("----------END---------");
        System.out.println("----9个环下环----START");
        DOWN(STATUS_ON, STATUS_ON.length);
        System.out.println("下环总步骤数：" + downCount);
        System.out.println("--------END------------");
    }

    private static final int[] STATUS_OFF = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static final int[] STATUS_ON = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1};
    private static int upCount = 0;
    private static int downCount = 0;
}
