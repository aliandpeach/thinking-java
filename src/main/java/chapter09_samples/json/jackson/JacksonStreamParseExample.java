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

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.JsonMappingException;

import java.io.File;
import java.io.IOException;

public class JacksonStreamParseExample {

    public static void main(String[] args) {
        try {
            JsonFactory jfactory = new JsonFactory();
            /*** read from file ***/
            JsonParser jParser = jfactory.createJsonParser(
                    new File("D:\\study\\effective\\src\\main\\resources\\user.json"));
            // loop until token equal to "}"
            while (jParser.nextToken() != JsonToken.END_OBJECT) {
                String fieldname = jParser.getCurrentName();
                if ("name".equals(fieldname)) {
                    // current token is "name",
                    // move to next, which is "name"'s value
                    jParser.nextToken();
                    System.out.println(jParser.getText()); // display mkyong
                }
                if ("age".equals(fieldname)) {
                    // current token is "age",
                    // move to next, which is "name"'s value
                    jParser.nextToken();
                    System.out.println(jParser.getIntValue()); // display 29
                }
                if ("messages".equals(fieldname)) {
                    jParser.nextToken(); // current token is "[", move next
                    // messages is array, loop until token equal to "]"
                    while (jParser.nextToken() != JsonToken.END_ARRAY) {
                        // display msg1, msg2, msg3
                        System.out.println(jParser.getText());
                    }
                }
            }
            jParser.close();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}