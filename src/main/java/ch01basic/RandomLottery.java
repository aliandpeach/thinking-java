/*
 * Created on 13-4-1
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 福利彩票
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-4-1
 */
public class RandomLottery {
    private static final Logger log = LoggerFactory.getLogger(RandomLottery.class);
    private static Random random;

    public static void main(String[] args) {
        random = new Random(new Date().getTime());
        System.out.println("蓝色球：" + generateMagicBlueNumber(random));
        Set<Integer> red = generateMagicRedNumber(random);
        List<Integer> redList = new ArrayList<Integer>(red);
        Collections.sort(redList);
        System.out.println("红色球：" + redList);
    }

    private static int generateMagicBlueNumber(Random random1) {
        return generateRandomInteger(1, 16, random1);
    }

    private static Set<Integer> generateMagicRedNumber(Random random1) {
        Set<Integer> result = getIntegers(new HashSet<Integer>(), random1);
        return result;
    }

    private static Set<Integer> getIntegers(Set<Integer> result, Random random1) {
        int nextInt = generateRandomInteger(1, 33, random1);
        while (result.contains(nextInt)) {
            nextInt = generateRandomInteger(1, 33, random);
        }
        result.add(nextInt);
        if (result.size() < 6) {
            getIntegers(result, random1);
        }
        return result;
    }


    private static int generateRandomInteger(int aStart, int aEnd, Random aRandom) {
        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = (long) aEnd - (long) aStart + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long) (range * aRandom.nextDouble());
        int randomNumber = (int) (fraction + aStart);
        return randomNumber;
    }
}
