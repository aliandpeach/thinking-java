package ch14reflect;

import java.lang.reflect.Field;

/**
 * 如何实现 2 + 2 = 5
 * -128至127会缓存到Integer的cache中去
 * cache[0] = -128，以此类推
 * @author XiongNeng
 * @version 1.0
 * @since 2014/6/3
 */
public class IntegerCache {
    public static void main(String[] args) throws Exception{
        Class cache = Integer.class.getDeclaredClasses()[0];
        System.out.println(cache.getName());
        Field c = cache.getDeclaredField("cache");
        c.setAccessible(true);
        Integer[] array = (Integer[]) c.get(cache);
        array[132] = array[133]; // array[132] = 4
        System.out.printf(" 2 + 2 = %d", 2 + 2);
    }
}
