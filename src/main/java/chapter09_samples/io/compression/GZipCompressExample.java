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
package chapter09_samples.io.compression;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-29
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GZipCompressExample {
    private static final String OUTPUT_GZIP_FILE = "/home/mkyong/file1.gz";
    private static final String SOURCE_FILE = "/home/mkyong/file1.txt";

    public static void main(String[] args) {
        GZipCompressExample gZip = new GZipCompressExample();
        gZip.gzipIt();
    }

    /**
     * GZip it
     */
    public void gzipIt() {

        byte[] buffer = new byte[1024];

        try {
            GZIPOutputStream gzos = new GZIPOutputStream(new FileOutputStream(OUTPUT_GZIP_FILE));

            FileInputStream in = new FileInputStream(SOURCE_FILE);

            int len;
            while ((len = in.read(buffer)) > 0) {
                gzos.write(buffer, 0, len);
            }

            in.close();

            gzos.finish();
            gzos.close();

            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}