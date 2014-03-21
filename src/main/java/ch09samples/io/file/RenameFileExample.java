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

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-29
 */

import java.io.File;

public class RenameFileExample {
    public static void main(String[] args) {

        File oldfile = new File("oldfile.txt");
        File newfile = new File("newfile.txt");

        if (oldfile.renameTo(newfile)) {
            System.out.println("Rename succesful");
        } else {
            System.out.println("Rename failed");
        }

    }
}
