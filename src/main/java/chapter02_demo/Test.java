/*
 * Created on 12-9-24
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
package chapter02_demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description of this file.
 * User: Xiong Neng
 * Date: 12-9-24
 * Time: 下午5:01
 */
public class Test {
    public static final Map<String,String> A = new HashMap<String,String>();

    public static void main(String[] args) {
        findJiraTasksByEnquiryId(PersonSon.class);
    }

    /**
     * 根据单方报价id，查询jira任务
     * @return
     */
    private static  <C extends Person> List<C> findJiraTasksByEnquiryId(Class<C> clazz) {
        //查询是否存在指定单方报价的jira任务
        String jQL = "" + hehe(clazz) + " ~ '" + "'";
        System.out.println(jQL);
        hehe(clazz);
        List<C> list = new ArrayList<C>();
        List<C> jiraTaskBeanList = new ArrayList<C>();
        PersonSon son = new PersonSon();
        son.setSonName("sonName");
        son.setName("sonName");
        jiraTaskBeanList.add((C)son);
        Person father = new Person();
        father.setName("sonName");
        jiraTaskBeanList.add((C)father);
        for (C bean: jiraTaskBeanList) {
            if (clazz.isInstance(bean)) {
                System.out.println("OK");
                System.out.println(bean.getName());
                list.add(bean);
            }
        }
        System.out.println(list);
        return list;
    }

    private static String hehe(Class<?> clazz) {
        System.out.println(clazz.getName());
        {
            System.out.println("what???");
        }
        return clazz.getName();
    }

}
