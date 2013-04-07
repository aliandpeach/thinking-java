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
package chapter09_samples.xml.jaxb;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-29
 */

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class ConvertXMLToObject {
    public static void main(String[] args) {
        try {
            File file = new File("C:\\file.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Customer customer = (Customer) jaxbUnmarshaller.unmarshal(file);
            System.out.println(customer);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
