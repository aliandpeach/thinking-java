/*
 * Created on 13-6-15
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
package chapter18_io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-6-15
 */
public class FolderZiper {
    public static void main(String[] a) throws Exception {
        zipFolders(new String[]{"D:\\usefulInfo", "D:\\input.txt"}, "D:\\usefulInfo.zip");
        zipFolder("D:\\usefulInfo", "D:\\usefulInfo.zip");
    }

    public static void zipFolders(String[] srcFiles, String destZipFile) throws Exception {
        ZipOutputStream zip;
        FileOutputStream fileWriter;

        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);

        for (String srcFile : srcFiles) {
            if (new File(srcFile).isDirectory()) {
                addFolderToZip("", srcFile, zip);
            } else {
                addFileToZipSimple(srcFile, zip);
            }
        }
        zip.flush();
        zip.close();
    }

    public static void zipFolder(String srcFile, String destZipFile) throws Exception {
        ZipOutputStream zip;
        FileOutputStream fileWriter;

        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);
        if (new File(srcFile).isDirectory()) {
            addFolderToZip("", srcFile, zip);
        } else {
            addFileToZipSimple(srcFile, zip);
        }

        zip.flush();
        zip.close();
    }

    private static void addFileToZip(String path, String srcFile, ZipOutputStream zip)
            throws Exception {

        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }

    private static void addFileToZipSimple(String srcFile, ZipOutputStream zip)
            throws Exception {
        File folder = new File(srcFile);
        byte[] buf = new byte[1024];
        int len;
        FileInputStream in = new FileInputStream(folder);
        zip.putNextEntry(new ZipEntry(folder.getName()));
        while ((len = in.read(buf)) > 0) {
            zip.write(buf, 0, len);
        }
    }

    private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
            throws Exception {
        File folder = new File(srcFolder);

        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
            } else {
                addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
            }
        }
    }
}
