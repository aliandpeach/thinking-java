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
package chapter22_swing;

import javax.swing.*;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-6-16
 */
public class NetDeploy {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JLabel rootLable1;
    private JTextField rootText1;
    private JLabel ipLable1;
    private JTextField ipText1;
    private JButton runButton1;
    private JPanel deployPanel1;
    private JPanel grailsPanel;
    private JPanel mavenPanel;
    private JPasswordField passwordField1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("net环境发布工具（一刀作品）");
        frame.setContentPane(new NetDeploy().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
