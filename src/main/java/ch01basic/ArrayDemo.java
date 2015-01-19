package ch01basic;

import java.util.Arrays;

public class ArrayDemo {
    /**
     * 数组基本用法
     */
    public void basic() {
        int[] array = {1, 2};
        System.out.println(array.getClass().getName());
    }

    /**
     * 数组的复制
     */
    public void copyArray() {
        int[] i = new int[7];
        int[] j = new int[10];
        Arrays.fill(i, 47); // 填充数组
        Arrays.fill(j, 10);
        System.out.println("i=" + Arrays.toString(i));
        System.out.println("j=" + Arrays.toString(j));
        // 复制数组，效率最高的方式
        System.arraycopy(i, 0, j, 0, i.length);
        System.out.println("数组复制完后的结果...");
        System.out.println("i=" + Arrays.toString(i));
        System.out.println("j=" + Arrays.toString(j));
    }

    /**
     * 数组的比较
     * 数组元素必须都overload了equals方法
     * 所有基本类型和Object类型都已经重载了equals方法
     */
    public void compare() {
        int[] i = new int[7];
        int[] j = new int[7];
        Arrays.fill(i, 10); // 填充数组
        Arrays.fill(j, 10);
        System.out.println(Arrays.equals(i, j));
    }

    /**
     * 数组排序和二分查找
     * 数组元素必须实现了Comparable接口
     */
    public void sort() {
        int[] i = {6, 3, 5, 6, 8, 9, 4, 1};
        System.out.println("i=" + Arrays.toString(i));
        Arrays.sort(i);
        // 排序完后可以二分查找
        int find = Arrays.binarySearch(i, 8);
        System.out.println("i=" + Arrays.toString(i));
        System.out.println("find=" + find);
    }
}
