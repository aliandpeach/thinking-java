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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PrettyOutputExample {
    public static void main(String[] args) {

        DataObject obj = new DataObject();
        // Gson gson = new Gson();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(obj);

        System.out.println(json);

    }
}
