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
// ColumnExample.java
// A test of the JTable class using default table models and a convenience
// constructor

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class ColumnExample extends JFrame {

    public ColumnExample() {
        super("Abstract Model JTable Test");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        DefaultTableModel dtm = new DefaultTableModel(new String[][]{
                {"1", "2", "3"}, {"4", "5", "6"}},
                new String[]{"Names", "In", "Order"});
        SortingColumnModel scm = new SortingColumnModel();
        JTable jt = new JTable(dtm, scm);
        jt.createDefaultColumnsFromModel();

        JScrollPane jsp = new JScrollPane(jt);
        getContentPane().add(jsp, BorderLayout.CENTER);
    }

    public static void main(String args[]) {
        ColumnExample ce = new ColumnExample();
        ce.setVisible(true);
    }
}