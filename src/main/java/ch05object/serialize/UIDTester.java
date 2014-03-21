/*
 * Created on 12-11-29
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
 * Copyright @2012 the original author or authors.
 */
package ch05object.serialize;

import java.io.*;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 12-11-29
 */
public class UIDTester {
    public static void main(String... strings) throws Exception {
        File file = new File("out.ser");
//        FileOutputStream fos = new FileOutputStream(file);
//        ObjectOutputStream oos = new ObjectOutputStream(fos);
//
//        SerializeMe serializeMe = new SerializeMe(1);
//        SerializeMe serializeMe2 = new SerializeMe(2);
//        oos.writeObject(serializeMe);
//        oos.writeObject(serializeMe2);
//        oos.close();

        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        SerializeMe dto = (SerializeMe) ois.readObject();
        SerializeMe dto2 = (SerializeMe) ois.readObject();
        System.out.println("data : " + dto.getData());
        System.out.println("data2 : " + dto2.getData() + "\nshould be 0：" + dto2.getData3());

        FileInputStream fisYou = new FileInputStream(file);
        ObjectInputStream oisYou = new ObjectInputStream(fisYou);

        SerializeYou dtoYou = (SerializeYou) oisYou.readObject();
        SerializeYou dtoYou2 = (SerializeYou) oisYou.readObject();
        System.out.println("data : " + dtoYou.getData());
        System.out.println("data2 : " + dtoYou2.getData() + "\nshould be 0：" + dtoYou2.getData3());
        ois.close();
    }
}
