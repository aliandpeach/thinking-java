/*
 * Created on 13-6-19
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

import chapter22_swing.table.button.DeployConfig;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-6-19
 */
public class AddressDialog extends JDialog {
    private DeployConfig deployConfig;
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
    JTextField passwordField = new JPasswordField();
    JTextField remoteConfigPathField = new JTextField();
    JComboBox<String> projectTypeField = new JComboBox<String>(new String[]{"grails", "maven"});

    public AddressDialog(Frame owner, boolean modal, DeployConfig deployConfig) {
        super(owner, modal);
        this.deployConfig = deployConfig;
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
    }

    public DeployConfig getDeployConfig() {
        return deployConfig;
    }

    public void setDeployConfig(DeployConfig deployConfig) {
        this.deployConfig = deployConfig;
    }
}

class JDialogTest extends JFrame {
    AddressDialog dialog = new AddressDialog(this, false, null);

    public JDialogTest(String title) {
        super(title);
        init();
    }

    public JDialogTest() {
        super();
        init();
    }

    private void init() {
        this.getContentPane().setLayout(new FlowLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final AddressDialog dialog = new AddressDialog(this, false, null);
        JButton button = new JButton("Show Dialog");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dialog.setSize(250, 120);
                dialog.setVisible(true);
            }
        });
        this.getContentPane().add(button);
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        JDialogTest frame = new JDialogTest();
        frame.pack();
        frame.setVisible(true);
    }

}
