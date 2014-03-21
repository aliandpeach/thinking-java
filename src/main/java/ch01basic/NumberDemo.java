/*
 * Created on 13-12-28
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
package ch01basic;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-12-28
 */
public class NumberDemo {
    public static void main(String[] args) {
        int ox = 0x100;
        System.out.println(ox);
        long oxl = 0x212_A_A_BB_BBA3L;
        System.out.println(oxl);
        /**
         * 以负数-5为例：
         1.先将-5的绝对值转换成二进制，即为0000 0101；
         2.然后求该二进制的反码，即为 1111 1010；
         3.最后将反码加1，即为：1111 1011
         所以Java中Integer.toBinaryString(-5)
         结果为11111111111111111111111111111011.
         Integer是32位(bit)的.
         */
        int bx = 0b11111111111111111111111111111011;
        System.out.println(bx);
        System.out.println(Integer.toBinaryString(-5));
        System.out.println(Integer.toBinaryString(5));

        // 交换两个数
        int a = 0b0111_1100;
        int b = 0b1100_1010;
        a ^= b;
        b ^= a;
        a ^= b;
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));

        byte allBitsOne = 0xFFFFFFFF;
        int allBitsOneInt = 0xFF;

        /**
         * << 左移，右边用0填充
         * >> 右移，左边用符号位填充
         * >>> 无符号右移，左边用0填充
         *
         * 原码 -> 补码， 补码 -> 原码：
         * 如果是负数，符号位不变，其余各位求反+1
         */
        System.out.println(-15 >> 2);
        System.out.println(Integer.toBinaryString(-15 >> 2));

    }
}
