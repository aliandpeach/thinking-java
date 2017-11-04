package java8.chap6;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;
import static java8.chap6.Dish.Type.*;
import static java8.chap6.Dish.menu;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * 收集器测试
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2017/11/4
 */
public class CollectorTest {

    @Test
    public void test1() {
        // 总数是多少
        long howManyDishes = menu.stream().collect(counting());
        assertThat(howManyDishes, is(9L));
        // 热量最高的那个
        Comparator<Dish> calorieComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCalorieDish = menu.stream().collect(maxBy(calorieComparator));
        assertThat(mostCalorieDish.get(), notNullValue());
        assertThat(mostCalorieDish.get().getCalories(), is(800));
        // 热量求和
        int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
        assertThat(totalCalories, greaterThan(2000));
        // 热量平均值
        double avgCalories = menu.stream().collect(averagingInt(Dish::getCalories));
        assertThat(avgCalories, lessThan(600.0));
        // 一次搞定个数、总和、平均值、最大值和最小值：
        IntSummaryStatistics menuStatistics =
                menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println(menuStatistics);
        // 字符串连接
        String shortMenu = menu.stream().map(Dish::getName).collect(joining(", "));
        System.out.println(shortMenu);
        // 典型的reducing三参数写法
        int totalCalories1 = menu.stream().collect(reducing(0, // 初始值
                Dish::getCalories, // 转换函数
                Integer::sum));    // 累积函数

        // 分组
        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
        System.out.println(dishesByType);

        // 自定义key和逻辑的分组
        Map<String, List<Dish>> dishesByCaloricLevel = menu.stream().collect(
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return "low";
                    else if (dish.getCalories() <= 700) return "normal";
                    else return "fat";
                }));
        System.out.println(dishesByCaloricLevel);

        // 自定义key和逻辑的分组并计算总数
        Map<String, Long> dishesByCaloricLevel2 = menu.stream().collect(
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return "low";
                    else if (dish.getCalories() <= 700) return "normal";
                    else return "fat";
                }, counting()));
        System.out.println(dishesByCaloricLevel2);

        // 自定义key和逻辑的分组并计算每个组最高热量的菜
        Map<String, Optional<Dish>> dishesByCaloricLevel3 = menu.stream().collect(
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return "low";
                    else if (dish.getCalories() <= 700) return "normal";
                    else return "fat";
                }, maxBy(Comparator.comparingInt(d -> d.getCalories()))));
        System.out.println(dishesByCaloricLevel3);

        // 自定义key和逻辑的分组并计算每个组最高热量的菜的名称
        Map<String, String> dishesByCaloricLevel4 = menu.stream().collect(
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return "low";
                    else if (dish.getCalories() <= 700) return "normal";
                    else return "fat";
                }, collectingAndThen(
                        maxBy(Comparator.comparingInt(d -> d.getCalories())), d -> d.get().getName())));
        System.out.println("aaaaaaa:" + dishesByCaloricLevel4);

        // groupingBy + mapping
        Map<Dish.Type, Set<String>> caloricLevelsByType =
                menu.stream().collect(
                        groupingBy(Dish::getType, mapping(
                                dish -> {
                                    if (dish.getCalories() <= 400) return "low";
                                    else if (dish.getCalories() <= 700) return "normal";
                                    else return "fat";
                                },
                                toSet())));

        // 多级分组
        Map<Dish.Type, Map<String, List<Dish>>> dishesByTypeCaloricLevel =
                menu.stream().collect(
                        groupingBy(Dish::getType,
                                groupingBy(dish -> {
                                    if (dish.getCalories() <= 400) return "low";
                                    else if (dish.getCalories() <= 700) return "normal";
                                    else return "fat";
                                })
                        )
                );
        System.out.println(dishesByTypeCaloricLevel);

        // 分区，只有两组，就是true和false
        Map<Boolean, List<Dish>> partitionedMenu =
                menu.stream().collect(partitioningBy(Dish::isVegetarian));
    }


    /**
     * 测试是否是质数
     * @param candidate
     * @return
     */
    public boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }

    public Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(partitioningBy(candidate -> isPrime(candidate)));
    }

    @Test
    public void test2() {
    }
}
