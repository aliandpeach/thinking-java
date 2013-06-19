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
 * 保网net环境快速发布小工具.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-6-16
 */

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class NetQuickDeploy {
    public static void main(String[] args) {
        UIUtils.setUI();
        final NetQuickDeploy example = new NetQuickDeploy();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                example.createAndShowGUI();
            }
        });
    }

    private JTable table;

    private void createAndShowGUI() {
        JFrame frame = new JFrame("net快发（熊能@保网 作品）");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        LinkedList<DeployConfig> deployConfigs = new LinkedList<DeployConfig>() {{
            add(new DeployConfig("go2配置", "D:\\work\\config\\path\\aaa\\bbb\\ccc",
                    "D:\\work\\configpath\\aaa\\bbb\\ccc", "10.68.14.68", "root",
                    "cninsure187", "/data/www/ins_share/config/go2/haha", "grails"));
        }};
        table = new JTable(new DeployTableModel(deployConfigs));
        JScrollPane scrollPane = new JScrollPane(table);

        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        table.getColumn("Edit").setCellRenderer(buttonRenderer);
        table.getColumn("Delete").setCellRenderer(buttonRenderer);
        table.getColumn("Config").setCellRenderer(buttonRenderer);
        table.getColumn("Project").setCellRenderer(buttonRenderer);
        table.getColumn("Together").setCellRenderer(buttonRenderer);
        table.addMouseListener(new JTableButtonMouseListener(table));

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(180);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(85);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);

        table.getColumnModel().getColumn(7).setMinWidth(0);
        table.getColumnModel().getColumn(7).setMaxWidth(0);
        table.getColumnModel().getColumn(7).setWidth(0);

        table.getColumnModel().getColumn(8).setPreferredWidth(70);
        table.getColumnModel().getColumn(9).setPreferredWidth(70);
        table.getColumnModel().getColumn(10).setPreferredWidth(80);
        table.getColumnModel().getColumn(11).setPreferredWidth(80);
        table.getColumnModel().getColumn(12).setPreferredWidth(100);

        table.getColumnModel().getColumn(0).setResizable(false);
        table.getColumnModel().getColumn(1).setResizable(false);
        table.getColumnModel().getColumn(2).setResizable(false);
        table.getColumnModel().getColumn(3).setResizable(false);
        table.getColumnModel().getColumn(4).setResizable(false);
        table.getColumnModel().getColumn(5).setResizable(false);
        table.getColumnModel().getColumn(6).setResizable(false);
        table.getColumnModel().getColumn(7).setResizable(false);
        table.getColumnModel().getColumn(8).setResizable(false);
        table.getColumnModel().getColumn(9).setResizable(false);
        table.getColumnModel().getColumn(10).setResizable(false);
        table.getColumnModel().getColumn(11).setResizable(false);
        table.getColumnModel().getColumn(12).setResizable(false);

        table.getTableHeader().setReorderingAllowed(false);

        table.setRowHeight(35);

        table.setCellSelectionEnabled(true);

        table.setShowGrid(true);
        table.setGridColor(Color.BLUE);
        table.setBorder(new EtchedBorder(EtchedBorder.RAISED));

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().setPreferredSize(new Dimension(1310, 650));
        frame.pack();
        frame.setVisible(true);
    }

    private class DeployTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private final String[] COLUMN_NAMES = new String[]{
                "配置描述", "本地配置文件根目录", "本地工程根目录", "服务器IP地址",
                "服务器用户名", "服务器密码", "服务器配置文件根目录", "工程类型",
                "Edit", "Delete", "Config", "Project", "Together"};
        private final Class<?>[] COLUMN_TYPES = new Class<?>[]{
                String.class, String.class, String.class, String.class,
                String.class, String.class, String.class, String.class,
                JButton.class, JButton.class, JButton.class, JButton.class, JButton.class};
        private LinkedList<DeployConfig> data;

        public DeployTableModel(LinkedList<DeployConfig> data) {
            this.data = data;
        }

        public LinkedList<DeployConfig> getData() {
            return data;
        }

        public void setData(LinkedList<DeployConfig> data) {
            this.data = data;
        }

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
                    // 第一列表示:配置的描述
                    return data.get(rowIndex).getDesc();
                case 1:
                    // 第二列表示:本地配置文件根目录
                    return data.get(rowIndex).getLocalConfigPath();
                case 2:
                    // 第三列表示:本地工程根目录
                    return data.get(rowIndex).getLocalProjectPath();
                case 3:
                    // 第四列表示:服务器IP地址
                    return data.get(rowIndex).getServerIp();
                case 4:
                    // 第五列表示:服务器用户名
                    return data.get(rowIndex).getUsername();
                case 5:
                    // 第六列表示:服务器密码
                    return data.get(rowIndex).getPassword();
                case 6:
                    // 第七列表示:服务器配置文件根目录
                    return data.get(rowIndex).getRemoteConfigPath();
                case 7:
                    // 第八列表示:发布的工程类型（grails，maven）
                    return data.get(rowIndex).getProjectType();
                case 8:
                    final JButton editButton = new JButton("修改");
                    editButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            data.add(new DeployConfig());
                            fireTableRowsInserted(rowIndex + 1, rowIndex + 1);
                        }
                    });
                    return editButton;
                case 9:
                    final JButton delButton = new JButton("删除");
                    delButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            int confirm = JOptionPane.showConfirmDialog(
                                    table, "你确定要删除吗？", "确认框", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                data.remove(rowIndex);
                                // trigger that fire... method
                                fireTableRowsDeleted(rowIndex, rowIndex);
                            }
                        }
                    });
                    return delButton;
                case 10:
                    final JButton configButton = new JButton("发配置");
                    configButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            int confirm = JOptionPane.showConfirmDialog(
                                    table, "你确定要删除吗？", "确认框", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                data.remove(rowIndex);
                                // trigger that fire... method
                                fireTableRowsDeleted(rowIndex, rowIndex);
                            }
                        }
                    });
                    return configButton;
                case 11:
                    final JButton projectButton = new JButton("发工程");
                    projectButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            int confirm = JOptionPane.showConfirmDialog(
                                    table, "你确定要删除吗？", "确认框", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                data.remove(rowIndex);
                                // trigger that fire... method
                                fireTableRowsDeleted(rowIndex, rowIndex);
                            }
                        }
                    });
                    return projectButton;
                case 12:
                    final JButton configProjectButton = new JButton("发配置+工程");
                    configProjectButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            int confirm = JOptionPane.showConfirmDialog(
                                    table, "你确定要删除吗？", "确认框", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                data.remove(rowIndex);
                                // trigger that fire... method
                                fireTableRowsDeleted(rowIndex, rowIndex);
                            }
                        }
                    });
                    return configProjectButton;
                default:
                    return "Error";
            }
        }
    }

    private class AddressDialog extends JDialog {
        JLabel label1 = new JLabel("配置描述");
        JLabel label2 = new JLabel("本地配置文件根目录");
        JLabel label3 = new JLabel("本地工程根目录");
        JLabel label4 = new JLabel("服务器IP地址");
        JLabel label5 = new JLabel("服务器用户名");
        JLabel label6 = new JLabel("服务器密码");
        JLabel label7 = new JLabel("服务器配置文件根目录");
        JLabel label8 = new JLabel("发布的工程类型");

        JButton yesButton = new JButton("确定");
        JButton cancelButton = new JButton("取消");

        JTextField descField = new JTextField();
        JTextField localConfigPathField = new JTextField();
        JTextField localProjectPathField = new JTextField();
        JTextField serverIpField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField remoteConfigPathField = new JTextField();
        JComboBox<String> projectTypeField = new JComboBox<String>(new String[]{"grails", "maven"});

        public AddressDialog(Frame owner, boolean modal) {
            super(owner, modal);
            init();
        }

        private void init() {
            this.setTitle("发布配置对话框");
            this.setLayout(new GridLayout(9, 2));
            this.add(label1);
            this.add(descField);
            this.add(label2);
            this.add(localConfigPathField);
            this.add(label3);
            this.add(localProjectPathField);
            this.add(label4);
            this.add(serverIpField);
            this.add(label5);
            this.add(usernameField);
            this.add(label6);
            this.add(passwordField);
            this.add(label7);
            this.add(remoteConfigPathField);
            this.add(label8);
            this.add(projectTypeField);
            this.add(yesButton);
            this.add(cancelButton);

            yesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                }
            });

            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                }
            });
        }

        public void setValues(DeployConfig deployConfig) {
            descField.setText(deployConfig.getDesc());
            localConfigPathField.setText(deployConfig.getLocalConfigPath());
            localProjectPathField.setText(deployConfig.getLocalProjectPath());
            serverIpField.setText(deployConfig.getServerIp());
            usernameField.setText(deployConfig.getUsername());
            passwordField.setText(deployConfig.getPassword());
            remoteConfigPathField.setText(deployConfig.getRemoteConfigPath());
            projectTypeField.setSelectedItem(deployConfig.getProjectType());
        }
    }
}