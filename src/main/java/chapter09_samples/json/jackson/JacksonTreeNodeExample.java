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
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class JacksonTreeNodeExample {
    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("D:\\study\\effective\\src\\main\\resources\\user.json"));
            JsonNode rootNode = mapper.readTree(fileReader);

            /*** read ***/
            JsonNode nameNode = rootNode.path("name");
            System.out.println(nameNode.getTextValue());

            JsonNode ageNode = rootNode.path("age");
            System.out.println(ageNode.getIntValue());

            JsonNode msgNode = rootNode.path("messages");
            Iterator<JsonNode> ite = msgNode.getElements();

            while (ite.hasNext()) {
                JsonNode temp = ite.next();
                System.out.println(temp.getTextValue());
            }

            /*** update ***/
            ((ObjectNode) rootNode).put("nickname", "new nickname");
            ((ObjectNode) rootNode).put("name", "updated name");
            ((ObjectNode) rootNode).remove("age");

            mapper.writeValue(new File("D:\\study\\effective\\src\\main\\resources\\user.json"), rootNode);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
