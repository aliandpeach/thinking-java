/*
 * Created on 13-3-29
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

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-29
 */

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class JsonToMap {
    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // read JSON from a file
            Map<String, Object> userInMap = mapper.readValue(
                    new File("D:\\study\\effective\\src\\main\\resources\\user.json"),
                    new TypeReference<Map<String, Object>>() {});

            System.out.println(userInMap.get("name"));
            System.out.println(userInMap.get("age"));

            ArrayList<String> list = (ArrayList<String>) userInMap.get("messages");

            for (String msg : list) {
                System.out.println(msg);
            }

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
