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
import java.util.zip.GZIPInputStream;

public class GZipDecompressExample {
    private static final String INPUT_GZIP_FILE = "/home/mkyong/file1.gz";
    private static final String OUTPUT_FILE = "/home/mkyong/file1.txt";

    public static void main(String[] args) {
        GZipDecompressExample gZip = new GZipDecompressExample();
        gZip.gunzipIt();
    }

    /**
     * GunZip it
     */
    public void gunzipIt() {

        byte[] buffer = new byte[1024];

        try {
            GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(INPUT_GZIP_FILE));

            FileOutputStream out = new FileOutputStream(OUTPUT_FILE);

            int len;
            while ((len = gzis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }

            gzis.close();
            out.close();

            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}