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

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-6-16
 */
// FileModel.java
// A custom table model to display information on a directory of files
//

import javax.swing.table.*;
import java.util.Date;
import java.io.File;

public class FileModel extends AbstractTableModel {

    String titles[] = new String[]{
            "Directory?", "File Name", "Read?", "Write?", "Size", "Last Modified"
    };

    Class types[] = new Class[]{
            Boolean.class, String.class, Boolean.class, Boolean.class,
            Number.class, Date.class
    };

    Object data[][];

    public FileModel() {
        this(".");
    }

    public FileModel(String dir) {
        File pwd = new File(dir);
        setFileStats(pwd);
    }

    // Implement the methods of the TableModel interface we're interested
    // in. Only getRowCount( ), getColumnCount( ), and getValueAt( ) are
    // required. The other methods tailor the look of the table.
    public int getRowCount() {
        return data.length;
    }

    public int getColumnCount() {
        return titles.length;
    }

    public String getColumnName(int c) {
        return titles[c];
    }

    public Class getColumnClass(int c) {
        return types[c];
    }

    public Object getValueAt(int r, int c) {
        return data[r][c];
    }

    // Our own method for setting/changing the current directory
    // being displayed. This method fills the data set with file info
    // from the given directory. It also fires an update event, so this
    // method could also be called after the table is on display.
    public void setFileStats(File dir) {
        String files[] = dir.list();
        data = new Object[files.length][titles.length];

        for (int i = 0; i < files.length; i++) {
            File tmp = new File(files[i]);
            data[i][0] = new Boolean(tmp.isDirectory());
            data[i][1] = tmp.getName();
            data[i][2] = new Boolean(tmp.canRead());
            data[i][3] = new Boolean(tmp.canWrite());
            data[i][4] = new Long(tmp.length());
            data[i][5] = new Date(tmp.lastModified());
        }

        // Just in case anyone's listening
        fireTableDataChanged();
    }
}


