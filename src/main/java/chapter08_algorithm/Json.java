/*
 * Created on 13-5-2
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
package chapter08_algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-5-2
 */
public class Json {
    // test
    public static void main(String[] args) {
        Json json1 = new Json();
        json1.add("totalCount", 2);
        json1.add("isTest", true);

        Json json_a = new Json();
        json_a.add("menuid", 1);
        json_a.add("menuname", "testmenu");
        json1.add("topics", json_a);

        Json json_b = new Json();
        json_b.add("menuid", 2);
        json_b.add("menuname", "testmenu");
        json1.add("topics", json_b);
        System.out.println(json1.toString());
    }

    private Map map = new LinkedHashMap();

    /**
     * 添加一个 JSON 属性，值为一个字符串，重复添加时产生数组
     * <p/>
     * <p/>
     * add("name", "value");
     * <p/>
     * 添加一个字符串，产生的 JSON 如：{"name":"value"}
     * <p/>
     * <p/>
     * <p/>
     * add("name", "value1");
     * <p/>
     * add("name", "value2");
     * <p/>
     * 添加两个同属性的字符串，产生的 JSON 如：{"name":["value1", "value2"]}
     *
     * @param key JSON 属性名
     * @param value 字符串格式的属性值
     */
    public void add(String key, String value) {
        addElement(key, value);
    }

    public void add(String key, int num) {
        addElement(key, num);
    }

    public void add(String key, boolean b) {
        addElement(key, b);
    }

    /**
     * 添加一个 JSON 属性，值为一个 JSON，重复添加时产生 JSON 数组
     * <p/>
     * <p/>
     * <p/>
     * Json json1 = new Json();
     * <p/>
     * json1.add("name1", "value1");
     * <p/>
     * json1.add("name2", "value2");
     * <p/>
     * Json json = new Json();
     * <p/>
     * json.add("message", json1);
     * <p/>
     * 添加一个 JSON，产生的 JSON 如：{"message":{"name1":"value1", "name2":"value2"}}
     * <p/>
     * <p/>
     * <p/>
     * Json json1 = new Json();
     * <p/>
     * json1.add("name1", "value1");
     * <p/>
     * json1.add("name2", "value2");
     * <p/>
     * Json json2 = new Json();
     * <p/>
     * json2.add("name1", "value3");
     * <p/>
     * json2.add("name2", "value4");
     * <p/>
     * Json json = new Json();
     * <p/>
     * json.add("message", json1);
     * <p/>
     * json.add("message", json2);
     * <p/>
     * 添加两个同属性的 JSON，产生的 JSON 如：{"message":[{"name1":"value1", "name2":"value2"}, {"name1":"value3", "name2":"value4"}]}
     *
     * @param key  JSON 属性名
     * @param json JSON 格式的属性值
     */
    public void add(String key, Json json) {
        addElement(key, json);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        int k = 0;
        for (Iterator i = map.keySet().iterator(); i.hasNext(); ) {
            String key = (String) (i.next());
            Object obj = map.get(key);
            if (k > 0) {
                sb.append(",");
            }
            appendKey(sb, key);
            if (obj instanceof String) {
                appendString(sb, (String) obj);
            } else if (obj instanceof List) {
                appendList(sb, (List) obj);
            } else if (obj instanceof Json) {
                appendJson(sb, (Json) obj);
            } else {
                appendOther(sb, obj);
            }
            k++;
        }
        sb.append("}");
        return sb.toString();
    }

    private void addElement(String key, Object obj) {
        if (!map.containsKey(key)) {
            if (obj instanceof Json) {
                List list = new ArrayList();
                list.add(obj);
                map.put(key, list);
            } else {
                map.put(key, obj);
            }
            return;
        }

        Object o = map.remove(key);

        if (o instanceof List) {
            ((List) o).add(obj);
            map.put(key, o);
            return;
        }

        // o is a String
        List list = new ArrayList();
        list.add(o);
        list.add(obj);
        map.put(key, list);
    }

    /**
     * Append JSON property name
     *
     * @param sb
     * @param key
     */
    private void appendKey(StringBuilder sb, String key) {
        sb.append("\"").append(key).append("\":");
    }

    /**
     * Append JSON property value that is a String
     *
     * @param sb
     * @param str
     */
    private void appendString(StringBuilder sb, String str) {
        sb.append("\"").append(str).append("\"");
    }

    /**
     * Append JSON property value that is a Integer
     *
     * @param sb
     * @param num
     */
    private void appendOther(StringBuilder sb, Object obj) {
        sb.append(obj);
    }

    /**
     * Append JSON property value that is a List
     *
     * @param sb
     * @param list
     */
    private void appendList(StringBuilder sb, List list) {
        sb.append("[");
        for (int j = 0, m = list.size(); j < m; j++) {
            if (j > 0) {
                sb.append(",");
            }
            Object obj = list.get(j);
            if (obj instanceof String) {
                appendString(sb, (String) obj);
            } else if (obj instanceof Json) {
                appendJson(sb, (Json) obj);
            } else {
                appendOther(sb, obj);
            }
        }
        sb.append("]");
    }

    /**
     * Append JSON property value that is a JSON
     *
     * @param sb
     * @param json
     */
    private void appendJson(StringBuilder sb, Json json) {
        sb.append(json.toString());
    }
}
