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
package ch09samples.io.file;

import java.io.File;
import java.io.IOException;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-29
 */
public class MakeFileReadonly {
    public static void main(String[] args) throws IOException {
        File file = new File("c:/file.txt");

        //mark this file as read only, since jdk 1.2
        file.setReadOnly();

        if (file.canWrite()) {
            System.out.println("This file is writable");
        } else {
            System.out.println("This file is read only");
        }

        //revert the operation, mark this file as writable, since jdk 1.6
        file.setWritable(true);

        if (file.canWrite()) {
            System.out.println("This file is writable");
        } else {
            System.out.println("This file is read only");
        }
    }
}
