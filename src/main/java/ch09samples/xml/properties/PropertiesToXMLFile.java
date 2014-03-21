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
package ch09samples.xml.properties;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-29
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesToXMLFile {
    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.setProperty("email.support", "donot-spam-me@nospam.com");

        //where to store?
        OutputStream os = new FileOutputStream("c:/email-configuration.xml");

        //store the properties detail into a pre-defined XML file
        props.storeToXML(os, "Support Email", "UTF-8");

        System.out.println("Done");
    }
}
