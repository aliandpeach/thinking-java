/*
 * Created on 13-4-7
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

import org.apache.commons.io.FileUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.Enumeration;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-4-7
 */
public class ZipUtils {
    /**
     * 压缩文件或者文件夹 压缩采用gb2312编码，其它编码方式可能造成文件名与文件夹名使用中文的情况下压缩后为乱码。。。
     *
     * @param source      要压缩的文件或者文件夹
     *                    建议使用"c:/abc"或者"c:/abc/aaa.txt"这种形式来给定压缩路径
     *                    使用"c:\\abc" 或者"c:\\abc\\aaa.txt"这种形式来给定路径的话，可能导致出现压缩和解压缩路径意外故障。。。
     * @param zipFileName 压缩后的zip文件名称 压缩后的目录组织与windows的zip压缩的目录组织相同。
     *                    会根据压缩的目录的名称，在压缩文件夹中创建一个改名的根目录， 其它压缩的文件和文件夹都在该目录下依照原来的文件目录组织形式
     * @throws java.io.IOException 压缩文件的过程中可能会抛出IO异常，请自行处理该异常。
     */
    public static void zip(String source, String zipFileName) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(new File(zipFileName));

        // 设置压缩的时候文件名编码为gb2312
        zos.setEncoding("gb2312");
        // System.out.println(zos.getEncoding());

        File f = new File(source);

        if (f.isDirectory()) {
            // 如果直接压缩文件夹
            // 此处使用/来表示目录，如果使用\\来表示目录的话，会导致压缩后的文件目录组织形式在解压缩的时候不能正确识别。
            ZIPDIR(source, zos, f.getName() + "/");
        } else {
            // 如果直接压缩文件
            ZIPDIR(f.getPath(), zos, new File(f.getParent()).getName() + "/");
            ZIPFile(f.getPath(), zos, new File(f.getParent()).getName() + "/" + f.getName());
        }

        zos.closeEntry();
        zos.close();
    }

    /**
     * 解压缩zip文件
     *
     * @param sourceFileName 要解压缩的zip文件
     * @param desDir         解压缩到的目录
     * @throws java.io.IOException 压缩文件的过程中可能会抛出IO异常，请自行处理该异常。
     */
    public static void unzip(String sourceFileName, String desDir) throws IOException {
        // 创建压缩文件对象
        ZipFile zf = new ZipFile(new File(sourceFileName), "gb2312");

        // 获取压缩文件中的文件枚举
        Enumeration<ZipEntry> en = zf.getEntries();
        int length;
        byte[] b = new byte[2048];

        // 提取压缩文件夹中的所有压缩实例对象
        while (en.hasMoreElements()) {
            ZipEntry ze = en.nextElement();
            // System.out.println("压缩文件夹中的内容："+ze.getName());
            // System.out.println("是否是文件夹："+ze.isDirectory());
            // 创建解压缩后的文件实例对象
            File f = new File(desDir + ze.getName());
            // System.out.println("解压后的内容："+f.getPath());
            // System.out.println("是否是文件夹："+f.isDirectory());
            // 如果当前压缩文件中的实例对象是文件夹就在解压缩后的文件夹中创建该文件夹
            if (ze.isDirectory()) {
                f.mkdirs();
            } else {
                // 如果当前解压缩文件的父级文件夹没有创建的话，则创建好父级文件夹
                if (!f.getParentFile().exists()) {
                    f.getParentFile().mkdirs();
                }

                // 将当前文件的内容写入解压后的文件夹中。
                OutputStream outputStream = new FileOutputStream(f);
                InputStream inputStream = zf.getInputStream(ze);
                while ((length = inputStream.read(b)) > 0) {
                    outputStream.write(b, 0, length);
                }

                inputStream.close();
                outputStream.close();
            }
        }
        zf.close();
    }

    /**
     * zip 压缩单个文件。 除非有特殊需要，否则请调用ZIP方法来压缩文件！
     *
     * @param sourceFileName 要压缩的原文件
     * @param zos            中间输出流
     * @param tager          压缩后的文件名
     * @throws java.io.IOException 抛出文件异常
     */
    private static void ZIPFile(String sourceFileName, ZipOutputStream zos, String tager) throws IOException {
        // System.out.println(tager);
        ZipEntry ze = new ZipEntry(tager);
        zos.putNextEntry(ze);

        // 读取要压缩文件并将其添加到压缩文件中
        FileInputStream fis = new FileInputStream(new File(sourceFileName));
        byte[] bf = new byte[2048];
        int location;
        while ((location = fis.read(bf)) != -1) {
            zos.write(bf, 0, location);
        }
        fis.close();
    }

    /**
     * 压缩目录。 除非有特殊需要，否则请调用ZIP方法来压缩文件！
     *
     * @param sourceDir 需要压缩的目录位置
     * @param zos       压缩到的zip文件
     * @param tager     压缩到的目标位置
     * @throws java.io.IOException 压缩文件的过程中可能会抛出IO异常，请自行处理该异常。
     */
    private static void ZIPDIR(String sourceDir, ZipOutputStream zos, String tager) throws IOException {
        // System.out.println(tager);
        ZipEntry ze = new ZipEntry(tager);
        zos.putNextEntry(ze);
        // 提取要压缩的文件夹中的所有文件
        File f = new File(sourceDir);
        File[] flist = f.listFiles();
        if (flist != null) {
            // 如果该文件夹下有文件则提取所有的文件进行压缩
            for (File fsub : flist) {
                if (fsub.isDirectory()) {
                    // 如果是目录则进行目录压缩
                    ZIPDIR(fsub.getPath(), zos, tager + fsub.getName() + "/");
                } else {
                    // 如果是文件，则进行文件压缩
                    ZIPFile(fsub.getPath(), zos, tager + fsub.getName());
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            long starTime;
            long endTime;

            System.out.println("开始压缩...");
            starTime = System.currentTimeMillis();
            zip("E:\\testzip", "E:\\MyFile.zip");
            endTime = System.currentTimeMillis();
            System.out.println("压缩完毕！花费时间: " + (endTime - starTime) + " 毫秒！");

            System.out.println("开始解压...");
            String targetDirStr = "E:\\MyFile2\\";
            File targetDir = new File(targetDirStr);
            if (!targetDir.exists()) targetDir.mkdir();
            FileUtils.cleanDirectory(targetDir);
            starTime = System.currentTimeMillis();
            unzip("E:\\MyFile.zip", targetDirStr);
            endTime = System.currentTimeMillis();
            System.out.println("解压完毕！花费时间: " + (endTime - starTime) + " 毫秒！");


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
