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

// SimpleTable.java
// A test of the JTable class using default table models and a convenience
// constructor
//
import java.awt.*;
import javax.swing.*;

public class SimpleTable extends JFrame {

    public SimpleTable( ) {
        super("Simple JTable Test");
        setSize(300, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTable jt = new JTable(new Object[][] { {"This", "is", new JButton("run")}, {"a", "Test", new JButton("run")} },
                new String[] {"Column", "Header", "action"});
        JScrollPane jsp = new JScrollPane(jt);
        getContentPane( ).add(jsp, BorderLayout.CENTER);
    }

    public static void main(String args[]) {
        SimpleTable st = new SimpleTable( );
        st.setVisible(true);
    }
}
