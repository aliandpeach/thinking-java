/*
 * Created on 13-4-25
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
package chapter10_newtech.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-4-25
 */

public class MainTest {

    public static void main(String[] args) {

        // Configuration
        Writer file = null;
        Configuration cfg = new Configuration();

        try {
            // Set Directory for templates
            cfg.setDirectoryForTemplateLoading(new File("src\\main\\java\\chapter10_newtech\\freemarker"));
            // load template
            Template template = cfg.getTemplate("helloworld.ftl");

            // data-model
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("message", "vogella example");
            root.put("container", "test");

            // create list
            List<ValueExampleObject> systems = new ArrayList<ValueExampleObject>();

            systems.add(new ValueExampleObject("Android"));
            systems.add(new ValueExampleObject("iOS States"));
            systems.add(new ValueExampleObject("Ubuntu"));
            systems.add(new ValueExampleObject("Windows7"));
            systems.add(new ValueExampleObject("OS/2"));

            root.put("systems", systems);

            ValueExampleObject exampleObject = new ValueExampleObject("Java object");
            root.put("exampleObject", exampleObject);
            root.put("indexOf", new IndexOfMethod());

            // File output
            file = new FileWriter(new File("src\\main\\java\\chapter10_newtech\\freemarker\\output.html"));
            template.process(root, file);
            file.flush();

            // Also write output to console
            Writer out = new OutputStreamWriter(System.out);
            template.process(root, out);
            out.flush();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (Exception e2) {
                }
            }
        }

    }
}
