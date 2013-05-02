/*
 * Created on 13-4-15
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
package chapter09_samples.json.jackson;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-4-15
 */
public class JsonWriter {
    public static void main(String[] args) throws Exception {
        ObjectMapper writer = new ObjectMapper();
        MyModel myBean = new MyModel();
        myBean.setAge(10);
        myBean.setName("name");
        PointModel model = new PointModel();
        model.setX(10);
        model.setY(10);
        List<PointModel> list = new ArrayList<PointModel>();
        list.add(model);
        model = new PointModel();
        model.setX(20);
        model.setY(20);
        list.add(model);
        myBean.setPointModelList(list);
        writer.writeValue(new FileOutputStream(
                "D:\\study\\thinking-java\\src\\main\\java\\chapter09_samples\\json\\test.json"), myBean);

    }
}
