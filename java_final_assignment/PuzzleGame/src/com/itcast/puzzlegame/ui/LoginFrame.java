package com.itcast.puzzlegame.ui;

import com.itcast.puzzlegame.Database.DatabaseFunction;
import com.itcast.puzzlegame.function.GetFileName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginFrame extends JFrame {
    // 初始化主要框架和数据库功能
    private TopFrame topFrame = new TopFrame(0);
    private DatabaseFunction databaseFunction = new DatabaseFunction();
    public int userid;
    public String password;

    // 构造函数，初始化布局和组件
    public LoginFrame() {
        setLayout(null);
        init();
        addComponent();
    }

    // 添加界面组件
    private void addComponent() {
        // 添加账号标签
        JLabel usernameLabel = new JLabel("账号");
        usernameLabel.setBounds(143, 71, 100, 40);
        usernameLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(usernameLabel);

        // 添加用户名输入框
        JTextField usernameField = new JTextField();
        usernameField.setBounds(189, 81, 160, 27);
        usernameField.setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(usernameField);

        // 添加密码标签
        JLabel passwordLabel = new JLabel("密   码");
        passwordLabel.setBounds(143, 107, 100, 40);
        passwordLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(passwordLabel);

        // 添加密码输入框
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(189, 116, 160, 27);
        passwordField.setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(passwordField);

        // 添加登录按钮
        JButton loginButton = new JButton("登录");
        loginButton.setBounds(160, 150, 60, 25);
        loginButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(85, 126, 248));
        loginButton.setBorder(null);
        loginButton.setFocusPainted(false);
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // 获取输入的用户名和密码
                userid = Integer.parseInt(usernameField.getText());
                password = new String(passwordField.getPassword());
                if (userid != 0 && password.length() != 0) {
                    // 验证用户名和密码
                    if (databaseFunction.validateUser(userid, password)) {
                        databaseFunction.getAvatar(userid);
                        TopFrame topFrame = new TopFrame(userid);
                        topFrame.setVisible(true);
                        dispose(); // 关闭当前登录窗口
                    } else {
                        // 用户验证失败，显示错误信息
                        JOptionPane.showMessageDialog(null,
                                "账号或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // 用户名或密码为空，显示错误信息
                    JOptionPane.showMessageDialog(null,
                            "账号或密码不能为空", "登录失败", JOptionPane.ERROR_MESSAGE);
                }
                System.out.println("登录");
            }
        });
        add(loginButton);

        // 添加注册按钮
        JButton registerButton = new JButton("注册");
        registerButton.setBounds(230, 150, 60, 25);
        registerButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(85, 126, 248));
        registerButton.setBorder(null);
        registerButton.setFocusPainted(false);
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // 跳转到注册界面
                SignupFrame signupFrame = new SignupFrame();
                signupFrame.setVisible(true);
                dispose(); // 关闭当前登录窗口
                System.out.println("注册");
            }
        });
        add(registerButton);

        // 添加取消按钮
        JButton cancelButton = new JButton("取消");
        cancelButton.setBounds(300, 150, 60, 25);
        cancelButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(85, 126, 248));
        cancelButton.setBorder(null);
        cancelButton.setFocusPainted(false);
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // 返回到主界面
                TopFrame topFrame = new TopFrame(0);
                topFrame.setVisible(true);
                dispose(); // 关闭当前登录窗口
                System.out.println("取消");
            }
        });
        add(cancelButton);
    }

    // 初始化窗口属性
    private void init() {
        setTitle("登录");
        setSize(514, 295);
        getContentPane().setBackground(Color.WHITE);
        setResizable(false);
        setLocationRelativeTo(null); // 窗口居中显示
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                // 关闭窗口时，返回到主界面并恢复文件名
                TopFrame topFrame = new TopFrame(0);
                topFrame.setVisible(true);
                GetFileName.recoverFileName();
            }
        });
    }
}
