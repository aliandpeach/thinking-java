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
import org.codehaus.jackson.type.TypeReference;

import java.io.FileInputStream;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-4-15
 */
public class JsonReader {
    public static void main(String[] args) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        MyModel genValue = mapper.readValue(
                new FileInputStream("D:\\study\\thinking-java\\src\\main\\java\\chapter09_samples\\json\\test.json"),
                new TypeReference<MyModel>(){});
        System.out.println(genValue.getPointModelList().size());
    }
}
