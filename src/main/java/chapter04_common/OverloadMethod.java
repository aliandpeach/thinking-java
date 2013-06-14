/*
 * Created on 13-6-12
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
package chapter04_common;

/**
 * 对于重载方法，从最具体类型开始，一级一级往上找，找到类型最匹配的，编译器决定了哪个方法！
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-6-12
 */
public class OverloadMethod {
    public void method(Object o) {
        System.out.println("Object 0");
    }
    public void method(CharSequence o) {
        System.out.println("CharSequence 0");
    }
//    public void method(String o) {
//        System.out.println("String 0");
//    }

    public static void main(String[] args) {
        String str = "";
        CharSequence sequence = str;
        Object o = str;

        new OverloadMethod().method(o);
        new OverloadMethod().method(sequence);
        new OverloadMethod().method(str);
    }
}
