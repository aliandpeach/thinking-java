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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetQuickDeploy {
    Logger log = LoggerFactory.getLogger(NetQuickDeploy.class);
    public static void main(String[] args) {
        UIUtils.setUI();
        final NetQuickDeploy example = new NetQuickDeploy();
        final LinkedList<DeployConfig> deployConfigs = example.loadConfig();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                example.createAndShowGUI(deployConfigs);
            }
        });
    }

    /**
     * 从home目录加载配置文件configs.conf
     * @return configs
     */
    private LinkedList<DeployConfig> loadConfig() {
        LinkedList<DeployConfig> result = new LinkedList<DeployConfig>();
        BufferedReader reader = null;
        try {
            String configFile = System.getProperty("user.home") + File.separator + "quickDeploy.conf";
            log.info("从home目录加载配置文件:" + configFile);
            File file = new File(configFile);
            if (file.createNewFile()) {
                return result;
            }
            reader = new BufferedReader(new FileReader(file));
            String s;
            while ((s = reader.readLine()) != null) {
                String[] eachConfig = s.split(";");
                if (eachConfig.length < 8)
                    continue;
                DeployConfig each = new DeployConfig(
                        eachConfig[0],eachConfig[1],eachConfig[2],eachConfig[3],
                        eachConfig[4],eachConfig[5],eachConfig[6],eachConfig[7]);
                result.add(each);
            }
        } catch (IOException e) {
            log.error("从home目录加载配置文件IOException", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("从home目录加载配置文件关闭流出错", e);
                }
            }
        }
        return result;
    }

    /**
     * 后台更新配置线程池
     */
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private void updateConfig(LinkedList<DeployConfig> data) {
        BufferedWriter writer = null;
        try {
            String configFile = System.getProperty("user.home") + File.separator + "quickDeploy.conf";
            writer = new BufferedWriter(new FileWriter(configFile));
            if (data != null && data.size() > 0) {
                for (DeployConfig each : data) {
                    writer.append(each.getDesc()).append(";");
                    writer.append(each.getLocalConfigPath()).append(";");
                    writer.append(each.getLocalProjectPath()).append(";");
                    writer.append(each.getServerIp()).append(";");
                    writer.append(each.getUsername()).append(";");
                    writer.append(each.getPassword()).append(";");
                    writer.append(each.getRemoteConfigPath()).append(";");
                    writer.append(each.getProjectType());
                    writer.newLine();
                }
                writer.flush();
            }
        } catch (IOException e) {
            log.error("后台更新配置线程池IOException", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.error("后台更新配置线程池关闭出错", e);
                }
            }
        }
    }

    private JTable table;
    private ConfigDialog configDialog;

    private void createAndShowGUI(LinkedList<DeployConfig> deployConfigs) {
        JFrame frame = new JFrame("net快发（by 一刀@保网）");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
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
        configDialog = new ConfigDialog(frame, true);
        configDialog.setSize(600, 350);
        configDialog.setResizable(false);
        JPanel headPanel = new JPanel();
        headPanel.setLayout(new BoxLayout(headPanel, BoxLayout.Y_AXIS));
        JButton newButton = new JButton("新建配置");
        newButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configDialog.popup(-1);
            }
        });
        headPanel.add(newButton);

        frame.getContentPane().add(headPanel, BorderLayout.NORTH);
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
                            configDialog.popup(rowIndex);
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
                                // 后台开始更新配置
                                executor.submit(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateConfig(data);
                                    }
                                });
                            }
                        }
                    });
                    return delButton;
                case 10:
                    final JButton configButton = new JButton("发配置");
                    configButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            int confirm = JOptionPane.showConfirmDialog(
                                    table, "你确定要发布配置文件吗？", "确认框", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                // todo
                                System.out.println("确定要发布配置文件");
                            }
                        }
                    });
                    return configButton;
                case 11:
                    final JButton projectButton = new JButton("发工程");
                    projectButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            int confirm = JOptionPane.showConfirmDialog(
                                    table, "你确定要发布工程吗？", "确认框", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                // todo
                                System.out.println("确定要发布工程");
                            }
                        }
                    });
                    return projectButton;
                case 12:
                    final JButton configProjectButton = new JButton("发配置+工程");
                    configProjectButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            int confirm = JOptionPane.showConfirmDialog(
                                    table, "你确定要发布配置+工程吗？", "确认框", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                // todo
                                System.out.println("确定要发布配置+工程");
                            }
                        }
                    });
                    return configProjectButton;
                default:
                    return "Error";
            }
        }
    }

    /**
     * 修改发布配置的对话框
     */
    private class ConfigDialog extends JDialog {
        private JLabel label1 = new JLabel("配置描述：", SwingConstants.RIGHT);
        private JLabel label2 = new JLabel("本地配置文件根目录：", SwingConstants.RIGHT);
        private JLabel label3 = new JLabel("本地工程根目录：", SwingConstants.RIGHT);
        private JLabel label4 = new JLabel("服务器IP地址：", SwingConstants.RIGHT);
        private JLabel label5 = new JLabel("服务器用户名：", SwingConstants.RIGHT);
        private JLabel label6 = new JLabel("服务器密码：", SwingConstants.RIGHT);
        private JLabel label7 = new JLabel("服务器配置文件根目录：", SwingConstants.RIGHT);
        private JLabel label8 = new JLabel("发布的工程类型：", SwingConstants.RIGHT);

        private JButton yesButton = new JButton("确定");
        private JButton cancelButton = new JButton("取消");

        private JTextField descField = new JTextField();
        private JTextField localConfigPathField = new JTextField();
        private JTextField localProjectPathField = new JTextField();
        private JTextField serverIpField = new JTextField();
        private JTextField usernameField = new JTextField();
        private JPasswordField passwordField = new JPasswordField();
        private JTextField remoteConfigPathField = new JTextField();
        private JComboBox<String> projectTypeField = new JComboBox<String>(
                new String[]{Constants.PRO_TYPE_GRAILS, Constants.PRO_TYPE_MAVEN});
        // 表格第几行被选中，-1代表新建
        private int rowSelect;

        public ConfigDialog(Frame owner, boolean modal) {
            super(owner, modal);
            init();
        }

        private void init() {
            this.setTitle("发布配置对话框");
            this.setLayout(new GridLayout(2, 1));

            JPanel configPanel = new JPanel(new GridLayout(8, 2));
            configPanel.add(label1);
            configPanel.add(descField);
            configPanel.add(label2);
            configPanel.add(localConfigPathField);
            configPanel.add(label3);
            configPanel.add(localProjectPathField);
            configPanel.add(label4);
            configPanel.add(serverIpField);
            configPanel.add(label5);
            configPanel.add(usernameField);
            configPanel.add(label6);
            configPanel.add(passwordField);
            configPanel.add(label7);
            configPanel.add(remoteConfigPathField);
            configPanel.add(label8);
            configPanel.add(projectTypeField);
            this.add(configPanel);

            JPanel btnPanel = new JPanel();
            btnPanel.add(yesButton);
            btnPanel.add(cancelButton);

            this.add(btnPanel);


            yesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final DeployTableModel tableModel = ((DeployTableModel)table.getModel());
                    if (rowSelect < 0) {
                        DeployConfig editConfig = new DeployConfig(descField.getText(), localConfigPathField.getText(),
                                localProjectPathField.getText(), serverIpField.getText(), usernameField.getText(),
                                String.valueOf(passwordField.getPassword()), remoteConfigPathField.getText(),
                                (String)projectTypeField.getSelectedItem());
                        tableModel.getData().add(editConfig);
                        tableModel.fireTableRowsInserted(tableModel.getRowCount(), tableModel.getRowCount());
                    } else {
                        DeployConfig selectConfig = tableModel.getData().get(rowSelect);
                        selectConfig.setDesc(descField.getText());
                        selectConfig.setLocalConfigPath(localConfigPathField.getText());
                        selectConfig.setLocalProjectPath(localProjectPathField.getText());
                        selectConfig.setServerIp(serverIpField.getText());
                        selectConfig.setUsername(usernameField.getText());
                        selectConfig.setPassword(String.valueOf(passwordField.getPassword()));
                        selectConfig.setRemoteConfigPath(remoteConfigPathField.getText());
                        selectConfig.setProjectType((String)projectTypeField.getSelectedItem());
                        tableModel.fireTableRowsUpdated(rowSelect, rowSelect);
                    }
                    // 后台开始更新配置
                    executor.submit(new Runnable() {
                        @Override
                        public void run() {
                            updateConfig(tableModel.getData());
                        }
                    });
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

        public void popup(int rowSelect) {
            this.rowSelect = rowSelect;
            if (rowSelect >= 0) {
                DeployConfig deployConfig = ((DeployTableModel)table.getModel()).getData().get(rowSelect);
                descField.setText(deployConfig.getDesc());
                localConfigPathField.setText(deployConfig.getLocalConfigPath());
                localProjectPathField.setText(deployConfig.getLocalProjectPath());
                serverIpField.setText(deployConfig.getServerIp());
                usernameField.setText(deployConfig.getUsername());
                passwordField.setText(deployConfig.getPassword());
                remoteConfigPathField.setText(deployConfig.getRemoteConfigPath());
                projectTypeField.setSelectedItem(deployConfig.getProjectType());
            } else {
                descField.setText("");
                localConfigPathField.setText("");
                localProjectPathField.setText("");
                serverIpField.setText("");
                usernameField.setText("");
                passwordField.setText("");
                remoteConfigPathField.setText("");
                projectTypeField.setSelectedIndex(0);
            }
            projectTypeField.setEnabled(rowSelect < 0);
            setVisible(true);
        }
    }
}