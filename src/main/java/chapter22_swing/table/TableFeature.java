/*
 * Created on 13-6-16
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
package chapter22_swing.table;

// TableFeature.java
// A test of the JTable class using default table models and a convenience
// constructor
//

import java.awt.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;

public class TableFeature extends JFrame {

    String titles[] = new String[]{
            "Directory?", "File Name", "Read?", "Write?", "Size", "Last Modified"
    };

    public TableFeature() {
        super("Simple JTable Test");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        File pwd = new File(".");
        Object[][] stats = getFileStats(pwd);

        JTable jt = new JTable(stats, titles);
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jt.setColumnSelectionAllowed(true);

        JScrollPane jsp = new JScrollPane(jt);
        getContentPane().add(jsp, BorderLayout.CENTER);
    }

    public Object[][] getFileStats(File dir) {
        String files[] = dir.list();
        Object[][] results = new Object[files.length][titles.length];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < files.length; i++) {
            File tmp = new File(files[i]);
            results[i][0] = tmp.isDirectory();
            results[i][1] = tmp.getName();
            results[i][2] = tmp.canRead();
            results[i][3] = tmp.canWrite();
            results[i][4] = tmp.length();
            results[i][5] = sdf.format(new Date(tmp.lastModified()));
        }
        return results;
    }

    public static void main(String args[]) {
        TableFeature tf = new TableFeature();
        tf.setVisible(true);
    }
}

