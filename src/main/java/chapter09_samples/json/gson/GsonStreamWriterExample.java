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
package chapter09_samples.json.gson;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-29
 */

import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;

public class GsonStreamWriterExample {
    public static void main(String[] args) {

        JsonWriter writer;
        try {
            writer = new JsonWriter(new FileWriter("c:\\user.json"));

            writer.beginObject(); // {
            writer.name("name").value("mkyong"); // "name" : "mkyong"
            writer.name("age").value(29); // "age" : 29

            writer.name("messages"); // "messages" :
            writer.beginArray(); // [
            writer.value("msg 1"); // "msg 1"
            writer.value("msg 2"); // "msg 2"
            writer.value("msg 3"); // "msg 3"
            writer.endArray(); // ]

            writer.endObject(); // }
            writer.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
