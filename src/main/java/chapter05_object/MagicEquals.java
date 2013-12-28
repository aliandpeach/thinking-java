/*
 * Created on 12-11-20
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
package chapter05_object;

import ch01_model.Color;
import ch01_model.ColoredPoint;
import ch01_model.MutablePoint;
import ch01_model.ReadonlyPoint;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 演示神奇的equals方法重写
 *
 * @author XiongNeng
 * @version 1.0
 * @since 12-11-20
 */
public class MagicEquals {

    /**
     * 陷阱1：定义错误equals方法签名(signature)
     */
    public void trap1() {
        ReadonlyPoint p1 = new ReadonlyPoint(1, 2);
        ReadonlyPoint p2 = new ReadonlyPoint(1, 2);

        ReadonlyPoint q = new ReadonlyPoint(2, 3);

        System.out.println(p1.equals(p2)); // prints true
        System.out.println(p1.equals(q)); // prints false

        HashSet<ReadonlyPoint> coll = new HashSet<ReadonlyPoint>();
        coll.add(p1);
        coll.add(p2);
        System.out.println(coll.contains(p1)); // prints true
        System.out.println(coll.contains(p2)); // prints true

        /**
         * 结果分析：
         * 在Java中重载被解析为静态的参数类型而非运行期的类型
         * HasSet在加入新的元素的时候，会调用泛型方法equals，
         * 而此时的泛型方法早就被erase到Object参数类型了。
         * 因此如果想要重写equals方法，参数必须是Object类型
         */
    }

    /**
     * 陷阱2：重载了equals的但没有同时重载hashCode的方法
     */
    public void trap2() {
        ReadonlyPoint p1 = new ReadonlyPoint(1, 2);
        ReadonlyPoint p2 = new ReadonlyPoint(1, 2);

        HashSet<ReadonlyPoint> coll = new HashSet<ReadonlyPoint>();
        coll.add(p1);
        System.out.println(coll.contains(p2)); // prints false
        /**
         * 结果分析：
         * 虽然p1和p2是相等的equals返回true，但是没有重写hashcode的值，
         * 那么根据默认的内存地址的hashcode极有可能不等
         */
    }

    /**
     * 陷阱3：建立在会变化字段上的equals定义
     */
    public void trap3() {
        MutablePoint point = new MutablePoint(1, 2);
        HashSet<MutablePoint> set = new HashSet<MutablePoint>();
        set.add(point);
        System.out.println(set.contains(point)); // prints true
        point.setX(8);
        System.out.println(set.contains(point)); // prints false
        // 但是通过迭代器来查找的话，是可以找到的，嘿嘿。
        Iterator<MutablePoint> it = set.iterator();
        boolean containedP = false;
        while (it.hasNext()) {
            MutablePoint nextP = it.next();
            if (nextP.equals(point)) {
                containedP = true;
                break;
            }
        }
        System.out.println(containedP); // prints true
        /**
         * 结果分析：
         * HashSet的contains方法是通过hashcode去查找定位的，如果期间hashcode改变了，那么很可能就找不到了
         * 而迭代器就是一个一个元素挨个循环，不会去查hashcode，那么是可以找到的
         */
    }

    /**
     * 陷阱4：不满足等价关系的equals错误定义
     * Object中的equals的规范阐述了equals方法必须实现在非null对象上的等价关系：
     * 1. 自反原则：对于任何非null值X,表达式x.equals(x)总返回true。
     * 2. 等价性：对于任何非空值x和y，那么当且仅当y.equals(x)返回真时，x.equals(y)返回真。
     * 3. 传递性：对于任何非空值x,y,和z，如果x.equals(y)返回真，且y.equals(z)也返回真，那么x.equals(z)也应该返回真。
     * 4. 一致性：对于非空x,y，多次调用x.equals(y)应该一致的返回真或假。提供给equals方法比较使用的信息不应该包含改过的信息。
     * 5. 对于任何非空值x,x.equals(null)应该总返回false.
     */
    public void trap4() {
        ReadonlyPoint p = new ReadonlyPoint(1, 2);

        ColoredPoint cp = new ColoredPoint(1, 2, Color.INDIGO);

        ReadonlyPoint pAnon = new ReadonlyPoint(1, 1) {
            @Override
            public int getY() {
                return 2;
            }
        };
        Set<ReadonlyPoint> coll = new java.util.HashSet<ReadonlyPoint>();
        coll.add(p);
        System.out.println(coll.contains(p)); // 打印 true
        System.out.println(coll.contains(cp)); // 打印 false
        System.out.println(coll.contains(pAnon)); // 打印 true
    }

    public static void main(String[] args) {
        MagicEquals magic = new MagicEquals();
        magic.trap1();
        magic.trap2();
        magic.trap3();
        magic.trap4();
    }

}
