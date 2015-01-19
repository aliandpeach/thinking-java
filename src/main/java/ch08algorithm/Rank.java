/*
 * Created on 13-7-23
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
package ch08algorithm;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-7-23
 */
public class Rank {
    public static void main(String[] args) {
        rank(null);
    }

    private static void rank(String[] arr) {
        long start = System.currentTimeMillis();
        rankWhole(new String[]{"Li", "Wang", "Jun", "Ku"});
        rankNum(new Character[]{'L', 'W', 'J', 'K', 'I', 'M', 'N', 'O', 'P'}, 6);
        long end = System.currentTimeMillis();
        System.out.println("time:" + (start - end) / 1000 + " seconds.");
    }


    /**
     * 全排列（非递归回溯）算法
     * 1、建立位置数组，即对位置进行排列，排列成功后转换为元素的排列；
     * 2、第n个位置搜索方式与八皇后问题类似。
     */
    private static boolean seek(int[] index, int n) {
        //flag为找到位置排列的标志，m保存正在搜索哪个位置
        boolean flag = false;
        int m = n;
        do {
            index[n]++;
            if (index[n] == index.length) { // 已无位置可用
                index[n--] = -1; //重置当前位置，回退到上一个位置
            } else if (!check(index, n)) {
                if (m == n) //当前位置搜索完成
                    flag = true;
                else
                    n++;
            }
        } while (!flag && n >= 0);
        return flag;
    }

    private static <T> void rankWhole(T[] objs) {
        int[] indexs = new int[objs.length];
        for (int i = 0; i < indexs.length; i++) {
            indexs[i] = -1;
        }
        for (int i = 0; i < indexs.length - 1; i++) {
            seek(indexs, i);
        }
        while (seek(indexs, indexs.length - 1)) {
            T[] temp = (T[]) Array.newInstance(objs[0].getClass(), objs.length);
            for (int j = 0; j < temp.length; j++) {
                temp[j] = objs[indexs[j]];
            }

            System.out.println("-----------------");
            System.out.println(Arrays.toString(temp));
        }
    }

    private static <T> void rankNum(T[] objs, int m) {
        int[] indexs = new int[objs.length];
        for (int i = 0; i < indexs.length; i++) {
            indexs[i] = -1;
        }
        for (int i = 0; i < m - 1; i++) {
            seek(indexs, i);
        }
        while (seek(indexs, m - 1)) {
            T[] temp = (T[]) Array.newInstance(objs[0].getClass(), m);
            for (int j = 0; j < m; j++) {
                temp[j] = objs[indexs[j]];
            }
            System.out.println("-----------------");
            System.out.println(Arrays.toString(temp));
        }
    }

    private static boolean check(int[] indx, int n) {
        for (int i = 0; i < n; i++)
            if (indx[i] == indx[n]) return true;
        return false;
    }
}