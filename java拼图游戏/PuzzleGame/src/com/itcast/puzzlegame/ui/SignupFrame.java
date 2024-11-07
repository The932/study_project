package com.itcast.puzzlegame.ui;

import com.itcast.puzzlegame.Database.DatabaseFunction;
import com.itcast.puzzlegame.function.GetFileName;
import com.itcast.puzzlegame.function.ImageSplitter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import static java.lang.Thread.sleep;

/**
 * 注册界面类
 */
public class SignupFrame extends JFrame {
    private DatabaseFunction databaseFunction = new DatabaseFunction(); // 数据库操作对象
    public String AvatarPath = "image/DefaultAvatar2.png"; // 默认头像路径
    private String username; // 用户名
    private String password0; // 密码
    private String password1; // 确认密码

    /**
     * 构造函数
     */
    public SignupFrame() {
        setLayout(null); // 绝对布局
        init(); // 初始化界面
        addComponent(); // 添加组件
    }

    /**
     * 添加组件
     */
    public void addComponent() {
        // 添加上传图片按钮
        ImageIcon imageIcon = new ImageIcon(AvatarPath);
        JButton uploadButton = new JButton("点击上传头像", imageIcon);
        uploadButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        uploadButton.setHorizontalTextPosition(SwingConstants.CENTER);
        uploadButton.setBounds(95, 30, 100, 100);
        uploadButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        uploadButton.setForeground(Color.BLACK);
        uploadButton.setContentAreaFilled(false);
        uploadButton.setBorder(null);
        uploadButton.setFocusPainted(false);
        uploadButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
                fileChooser.addChoosableFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    AvatarPath = selectedFile.getAbsolutePath();
                    ImageSplitter imageSplitter = new ImageSplitter();
                    AvatarPath = imageSplitter.ImageZoom(2112400304, AvatarPath);
                    System.out.println(AvatarPath);
                    // 更新头像
                    ImageIcon imageIcon = new ImageIcon(AvatarPath);
                    uploadButton.setIcon(imageIcon);
                    uploadButton.setText("");
                }
                System.out.println("上传头像");
            }
        });
        add(uploadButton);

        // 添加用户名标签
        JLabel usernameLabel = new JLabel("用户名");
        usernameLabel.setBounds(20, 130, 100, 40);
        usernameLabel.setFont(new Font("微软雅黑", Font.BOLD, 13));
        add(usernameLabel);

        // 添加用户名输入框
        JTextField usernameField = new JTextField();
        usernameField.setBounds(70, 138, 160, 27);
        usernameField.setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(usernameField);

        // 添加密码标签
        JLabel passwordLabel = new JLabel("密   码");
        passwordLabel.setBounds(20, 160, 100, 40);
        passwordLabel.setFont(new Font("微软雅黑", Font.BOLD, 13));
        add(passwordLabel);

        // 添加密码输入框
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(70, 168, 160, 27);
        passwordField.setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(passwordField);

        // 添加确认密码标签
        JLabel confirmPasswordLabel = new JLabel("确认密码");
        confirmPasswordLabel.setBounds(11, 190, 100, 40);
        confirmPasswordLabel.setFont(new Font("微软雅黑", Font.BOLD, 13));
        add(confirmPasswordLabel);

        // 添加确认密码输入框
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(70, 198, 160, 27);
        confirmPasswordField.setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(confirmPasswordField);

        // 添加文字提示
        JLabel tipLabel = new JLabel("密码长度需大于8位，且不能包含空格");
        tipLabel.setBounds(75, 215, 250, 40);
        tipLabel.setFont(new Font("微软雅黑", Font.PLAIN, 9));
        add(tipLabel);

        // 添加注册按钮
        JButton loginButton = new JButton("注册");
        loginButton.setBounds(80, 250, 60, 25);
        loginButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(85, 126, 248));
        loginButton.setBorder(null);
        loginButton.setFocusPainted(false);
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                super.mouseClicked(e);
                username = usernameField.getText();
                password0 = new String(passwordField.getPassword());
                password1 = new String(confirmPasswordField.getPassword());
                ImageSplitter imageSplitter = new ImageSplitter();
                if (!username.isEmpty() && password0.length() >= 8 && !password1.isEmpty() && !password0.contains(" ") && !password1.contains(" ")) {
                    if (password0.equals(password1)) {
                        if(databaseFunction.addUser(username, password0, AvatarPath)) {
                            // 注册成功，弹窗提示注册成功,点击确定后跳转到登录界面
                            JTextArea textArea = new JTextArea("注册成功\n你的账号是："+ databaseFunction.userID);
                            textArea.setEditable(false);
                            JOptionPane.showMessageDialog(null, textArea, "注册成功", JOptionPane.INFORMATION_MESSAGE);
                            LoginFrame loginFrame = new LoginFrame();
                            loginFrame.setVisible(true);
                            dispose();
                            try {
                                sleep(1000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            // 注册失败，显示错误信息
                            JOptionPane.showMessageDialog(null,
                                    "error", "注册失败", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        // 注册失败，显示错误信息
                        JOptionPane.showMessageDialog(null,
                                "两次密码不一致", "注册失败", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "用户名或密码长度需大于8位且不能包含空格", "注册失败", JOptionPane.ERROR_MESSAGE);
                }
                System.out.println("注册");
            }
        });
        add(loginButton);

        // 添加返回按钮
        JButton registerButton = new JButton("登录");
        registerButton.setBounds(150, 250, 60, 25);
        registerButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(85, 126, 248));
        registerButton.setBorder(null);
        registerButton.setFocusPainted(false);
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                super.mouseClicked(e);
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                dispose();
                System.out.println("返回登录");
            }
        });
        add(registerButton);
    }

    /**
     * 初始化界面
     */
    public void init() {
        setTitle("注册");
        setSize(290, 440);
        getContentPane().setBackground(Color.WHITE);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                GetFileName.recoverFileName(); // 恢复文件名
            }
        });
    }
}
