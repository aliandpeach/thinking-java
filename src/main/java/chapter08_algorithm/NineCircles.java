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
    private static final int CIRCLE_NUM = 9;
    private static int upCount = 0;
    private static int downCount = 0;

    public static void main(String[] args) {
        letMeSee(CIRCLE_NUM);
        letMeSee(new int[]{0, 0, 0}, new int[]{1, 1, 1});
    }

    private static void letMeSee(int circleNum) {
        int[] statusOff = new int[circleNum];
        int[] statusOn = new int[circleNum];
        for (int i = 0; i < circleNum; i++) {
            statusOff[i] = 0;
            statusOn[i] = 1;
        }
        System.out.println("----上环----START");
        UP(statusOff, statusOff.length);
        System.out.println("上环总步骤数：" + upCount);
        System.out.println("----------END---------");
        System.out.println("----下环----START");
        DOWN(statusOn, statusOn.length);
        System.out.println("下环总步骤数：" + downCount);
        System.out.println("--------END------------");
    }

    private static void letMeSee(int[] statusOff, int[] statusOn) {
        System.out.println("----上环----START");
        UP(statusOff, statusOff.length);
        System.out.println("上环总步骤数：" + upCount);
        System.out.println("----------END---------");
        System.out.println("----下环----START");
        DOWN(statusOn, statusOn.length);
        System.out.println("下环总步骤数：" + downCount);
        System.out.println("--------END------------");
    }


    /**
     * 上环一个
     *
     * @param status
     * @param index
     */
    private static void up(int[] status, int index) {
        if (index <= 0) return;
        if (index == 1) {
            justUp(status, 1);
        } else if (isAllOff(status, index - 2) && isOn(status, index - 1)) {
            justUp(status, index);
        } else {
            if (!isOn(status, index - 1)) up(status, index - 1);
            if (!isAllOff(status, index - 2)) DOWN(status, index - 2);
            justUp(status, index);
        }
    }

    /**
     * 下环一个
     *
     * @param status
     * @param index
     */
    private static void down(int[] status, int index) {
        if (index <= 0) return;
        if (index == 1) {
            justDown(status, index);
        } else if (isAllOff(status, index - 2) && isOn(status, index - 1)) {
            justDown(status, index);
        } else {
            if (!isOn(status, index - 1)) up(status, index - 1);
            if (!isAllOff(status, index - 2)) DOWN(status, index - 2);
            justDown(status, index);
        }
    }

    /**
     * 上环前number个
     *
     * @param status
     * @param number
     */
    private static void UP(int[] status, int number) {
        if (number == 1) {
            justUp(status, 1);
        } else if (number == 2) {
            justUp(status, 1);
            justUp(status, 2);
        } else if (number > 2) {
            if (!isOn(status, number - 1)) up(status, number - 1);
            if (!isAllOff(status, number - 2)) {
                int validNum = number - 2;
                while (validNum > 0) {
                    if (status[validNum - 1] == 1) {
                        break;
                    }
                    validNum--;
                }
                DOWN(status, validNum);
            }
            if (!isOn(status, number)) up(status, number);
            if (!isAllOn(status, number - 1)) {
                int validNum = number - 1;
                while (validNum > 0) {
                    if (status[validNum - 1] == 0) {
                        break;
                    }
                    validNum--;
                }
                UP(status, validNum);
            }
        }
    }

    /**
     * 下环前number个
     *
     * @param status
     * @param number
     */
    private static void DOWN(int[] status, int number) {
        if (number == 1) {
            justDown(status, 1);
        } else if (number == 2) {
            justUp(status, 1);
            justDown(status, 2);
            justDown(status, 1);
        } else if (number > 2) {
            if (!isOn(status, number - 1)) up(status, number - 1);
            if (!isAllOff(status, number - 2)) DOWN(status, number - 2);
            if (!isOff(status, number)) down(status, number);
            if (!isAllOff(status, number - 1)) DOWN(status, number - 1);
        }
    }

    private static void justUp(int[] status, int index) {
        if (status[index - 1] == 0) {
            for (int k : status) System.out.print(k);
            System.out.println();
            System.out.println("----上环：" + index);
            status[index - 1] = 1;
            upCount++;
        }
    }

    private static void justDown(int[] status, int index) {
        if (status[index - 1] == 1) {
            for (int k : status) System.out.print(k);
            System.out.println();
            System.out.println("----下环：" + index);
            status[index - 1] = 0;
            downCount++;
        }
    }

    /**
     * 数组arr前num个数是否都是1
     *
     * @param arr
     * @param num
     * @return
     */
    private static boolean isAllOn(int[] arr, int num) {
        if (num <= 0) return true;
        boolean isAllOn = true;
        for (int i = 0; i < num; i++) {
            if (arr[i] == 0) {
                isAllOn = false;
                break;
            }
        }
        return isAllOn;
    }

    private static boolean isOn(int[] arr, int num) {
        return num <= 0 || arr[num - 1] == 1;
    }

    private static boolean isOff(int[] arr, int num) {
        return num <= 0 || arr[num - 1] == 0;
    }

    /**
     * 数组arr前num个数是否都是0
     *
     * @param arr
     * @param num
     * @return
     */
    private static boolean isAllOff(int[] arr, int num) {
        if (num <= 0) return true;
        boolean isAllOff = true;
        for (int i = 0; i < num; i++) {
            if (arr[i] == 1) {
                isAllOff = false;
                break;
            }
        }
        return isAllOff;
    }

}
