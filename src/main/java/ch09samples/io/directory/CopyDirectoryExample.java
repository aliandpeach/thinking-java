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
package ch09samples.io.directory;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-29
 */

import java.io.*;

public class CopyDirectoryExample {
    public static void main(String[] args) {
        File srcFolder = new File("c:\\mkyong");
        File destFolder = new File("c:\\mkyong-new");

        //make sure source exists
        if (!srcFolder.exists()) {
            System.out.println("Directory does not exist.");
            //just exit
            System.exit(0);
        } else {
            try {
                copyFolder(srcFolder, destFolder);
            } catch (IOException e) {
                e.printStackTrace();
                //error, just exit
                System.exit(0);
            }
        }
        System.out.println("Done");
    }

    public static void copyFolder(File src, File dest) throws IOException {

        if (src.isDirectory()) {
            //if directory not exists, create it
            if (!dest.exists()) {
                dest.mkdir();
                System.out.println("Directory copied from " + src + "  to " + dest);
            }
            //list all the directory contents
            String files[] = src.list();
            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copyFolder(srcFile, destFile);
            }

        } else {
            //if file, then copy it
            //Use bytes stream to support all file types
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();
            System.out.println("File copied from " + src + " to " + dest);
        }
    }
}
