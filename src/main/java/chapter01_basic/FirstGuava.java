/*
 * Created on 12-10-12
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

import ch01_model.Person;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 12-10-12
 */
public class FirstGuava {
    public void filter() {
        List<Person> list = new ArrayList<Person>();
        list.add(new Person(11, "张三", 11));
        list.add(new Person(22, "张三", 22));
        list.add(new Person(33, "张三", 33));
        list.add(new Person(44, "张三", 44));
        list.add(new Person(99, "王五", 99));

        final Map<String, Object> params = new HashMap<String,Object>();
        //params.put("id", "33");
        params.put("name", "张三");
        Map<String, Integer> ageParam = new HashMap<String, Integer>();
        ageParam.put("from", 23);
        ageParam.put("to",44);
        params.put("age", ageParam);

        Collection<Person> filterCollection = Collections2.filter(list, new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                Class clazz = input.getClass();
                Set<String> keys = params.keySet();
                for (String key : keys) {
                    if (params.get(key) != null && params.get(key).getClass().equals(HashMap.class)) {
                        try {
                            Field f = clazz.getDeclaredField(key);
                            f.setAccessible(true);
                            int age = (Integer)f.get(input);
                            Map map = (Map) params.get(key);
                            int from = (Integer)map.get("from");
                            int to = (Integer)map.get("to");
                            if (age < from || age > to) {
                                return false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (params.get(key) != null) {
                        try {
                            Field f = clazz.getDeclaredField(key);
                            f.setAccessible(true);
                            if (!String.valueOf(f.get(input)).equals(params.get(key))) {
                                return false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                return true;
            }
        });

        System.out.println(filterCollection.size());
    }

    public static void main(String[] args) {
        new FirstGuava().filter();
    }
}
