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
package ch18io.compression;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-29
 */

import ch18io.model.Address;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;

public class DecompressObjectFromFile implements Serializable {

    public static void main(String args[]) {

        DecompressObjectFromFile deserializer = new DecompressObjectFromFile();
        Address address = deserializer.deserialzeAddress();
        System.out.println(address);
    }

    public Address deserialzeAddress() {

        Address address;

        try {

            FileInputStream fin = new FileInputStream("c:\\address.gz");
            GZIPInputStream gis = new GZIPInputStream(fin);
            ObjectInputStream ois = new ObjectInputStream(gis);
            address = (Address) ois.readObject();
            ois.close();

            return address;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}