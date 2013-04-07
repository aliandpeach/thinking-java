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
package chapter09_samples.io.file;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-29
 */

import java.io.File;
import java.io.IOException;

public class FilePathExample {
    public static void main(String[] args) {
        try {

            String filename = "testing.txt";
            String finalfile;
            String workingDir = System.getProperty("user.dir");

            String your_os = System.getProperty("os.name").toLowerCase();
            if (your_os.contains("win")) {
                finalfile = workingDir + "\\" + filename;
            } else if (your_os.contains("nix") || your_os.contains("nux")) {
                finalfile = workingDir + "/" + filename;
            } else {
                finalfile = workingDir + "{others}" + filename;
            }

            System.out.println("Final filepath : " + finalfile);
            File file = new File(finalfile);

            if (file.createNewFile()) {
                System.out.println("Done");
            } else {
                System.out.println("File already exists!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
