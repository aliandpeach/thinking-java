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

import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GsonStreamReaderExample {
    public static void main(String[] args) {
        try {
            JsonReader reader = new JsonReader(new FileReader("c:\\user.json"));
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("name")) {
                    System.out.println(reader.nextString());
                } else if (name.equals("age")) {
                    System.out.println(reader.nextInt());
                } else if (name.equals("messages")) {
                    // read array
                    reader.beginArray();
                    while (reader.hasNext()) {
                        System.out.println(reader.nextString());
                    }
                    reader.endArray();
                } else {
                    reader.skipValue(); //avoid some unhandle events
                }
            }
            reader.endObject();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}