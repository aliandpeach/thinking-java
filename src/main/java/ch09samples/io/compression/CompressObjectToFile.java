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
package ch09samples.io.compression;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-29
 */


import ch09samples.io.model.Address;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPOutputStream;

public class CompressObjectToFile implements Serializable {

    public static void main(String args[]) {

        CompressObjectToFile serializer = new CompressObjectToFile();
        serializer.serializeAddress("wall street", "united state");
    }

    public void serializeAddress(String street, String country) {

        Address address = new Address();
        address.setStreet(street);
        address.setCountry(country);

        try {
            FileOutputStream fos = new FileOutputStream("c:\\address.gz");
            GZIPOutputStream gz = new GZIPOutputStream(fos);

            ObjectOutputStream oos = new ObjectOutputStream(gz);

            oos.writeObject(address);
            oos.close();

            System.out.println("Done");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}