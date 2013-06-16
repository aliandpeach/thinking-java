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
package chapter22_swing.table.button;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-6-16
 */

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ButtonExample {

    public static void main(String[] args) {
        final ButtonExample example = new ButtonExample();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                example.createAndShowGUI();
            }
        });
    }
    private JFrame frame;
    private JTable table;
    private void createAndShowGUI() {
        frame = new JFrame("Button Example");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        table = new JTable(new JTableModel());
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        table.getColumn("Button1").setCellRenderer(buttonRenderer);
        table.getColumn("Button2").setCellRenderer(buttonRenderer);
        table.addMouseListener(new JTableButtonMouseListener(table));

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().setPreferredSize(new Dimension(500, 200));
        frame.pack();
        frame.setVisible(true);
    }

    private class JTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private final String[] COLUMN_NAMES = new String[]{"Id", "Stuff", "Button1", "Button2"};
        private final Class<?>[] COLUMN_TYPES = new Class<?>[]{Integer.class, String.class, JButton.class, JButton.class};
        private final LinkedList<String[]> data = new LinkedList<String[]>() {{
            add(new String[]{"11", "11AA"});
            add(new String[]{"22", "22AA"});
            add(new String[]{"33", "33AA"});
        }};

        @Override
        public int getColumnCount() {
            return COLUMN_NAMES.length;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public String getColumnName(int columnIndex) {
            return COLUMN_NAMES[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return COLUMN_TYPES[columnIndex];
        }

        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            switch (columnIndex) {
                case 0:
                case 1:
                    return data.get(rowIndex)[columnIndex];
                case 2:
                    final JButton button1 = new JButton("增加一行");
                    button1.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            data.add(rowIndex + 1, new String[]{"", ""});
                            fireTableRowsInserted(rowIndex + 1, rowIndex + 1);
                        }
                    });
                    return button1;
                case 3:
                    final JButton button = new JButton("删除一行");
                    button.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            if (JOptionPane.showConfirmDialog(table, "你确定要删除吗？", "确认框", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                data.remove(rowIndex);
                                fireTableRowsDeleted(rowIndex, rowIndex); // trigger that fire... method
                            }
                        }
                    });
                    return button;
                default:
                    return "Error";
            }
        }
    }
}