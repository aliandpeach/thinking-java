/*
 * Created on 14-3-21
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
 * Copyright @2014 the original author or authors.
 */
package ch20annotation.simple;

import java.lang.annotation.*;

/**
 * 定义注解格式：public @interface 注解名 {定义体}
 * 注解参数的可支持数据类型：
 * 1.所有基本数据类型（byte,char,short,int,long,float,double,boolean)
 * 2.String类型
 * 3.Class类型
 * 4.enum类型
 * 5.Annotation类型
 * 6.以上所有类型的数组
 *
 * @author XiongNeng
 * @version 1.0
 * @since 14-3-21
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Regex {
    // 这个就是注解参数
    RegexRule regexRule() default RegexRule.NUMBER;
}
