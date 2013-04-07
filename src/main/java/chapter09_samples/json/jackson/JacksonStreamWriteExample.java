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

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;

import java.io.File;
import java.io.IOException;

public class JacksonStreamWriteExample {
    public static void main(String[] args) {
        try {
            JsonFactory jfactory = new JsonFactory();

            /*** write to file ***/
            JsonGenerator jGenerator = jfactory.createJsonGenerator(new File(
                    "D:\\study\\effective\\src\\main\\resources\\user.json"), JsonEncoding.UTF8);
            jGenerator.writeStartObject(); // {

            jGenerator.writeStringField("name", "mkyong"); // "name" : "mkyong"
            jGenerator.writeNumberField("age", 29); // "age" : 29

            jGenerator.writeFieldName("messages"); // "messages" :
            jGenerator.writeStartArray(); // [

            jGenerator.writeString("msg 1"); // "msg 1"
            jGenerator.writeString("msg 2"); // "msg 2"
            jGenerator.writeString("msg 3"); // "msg 3"

            jGenerator.writeEndArray(); // ]

            jGenerator.writeEndObject(); // }

            jGenerator.close();

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
