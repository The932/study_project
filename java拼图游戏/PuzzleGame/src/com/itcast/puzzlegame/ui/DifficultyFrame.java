package com.itcast.puzzlegame.ui;

import com.itcast.puzzlegame.Database.DatabaseFunction;
import com.itcast.puzzlegame.function.GetFileName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DifficultyFrame extends JFrame {
    // 静态变量，用于存储网格大小
    public static int gridSize = 0;
    // 用户ID
    public int userid;

    // 构造函数，初始化窗口和组件
    public DifficultyFrame(int userid) {
        this.userid = userid;
        setLayout(null);    // 设置布局为空
        init();
        isAuthenticated();
        addComponent();
    }

    // 检查用户是否已登录，并根据用户状态添加对应的按钮
    public void isAuthenticated() {
        if (userid != 0) {
            System.out.println("用户ID: " + this.userid);
            // 添加新的按钮
            DatabaseFunction databaseFunction = new DatabaseFunction();
            String avatarPath = databaseFunction.getAvatar(userid);
            ImageIcon newButtonIcon = new ImageIcon(avatarPath); // 新按钮的图标
            JButton newButton = new JButton(newButtonIcon);
            newButton.setBounds(1200, 508, 50, 50);
            newButton.setContentAreaFilled(false);
            newButton.setBorder(null);
            newButton.setFocusPainted(false);
            newButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    UserFrame userFrame = new UserFrame(userid);    // 跳转用户信息界面
                    userFrame.setVisible(true);
                    // 在这里添加新按钮的点击事件
                    System.out.println("新按钮被点击");
                }
            });
            add(newButton);
        } else {
            //添加登录按钮
            ImageIcon loginIco = new ImageIcon("image/DefaultAvatar2.png");
            JButton loginButton = new JButton(loginIco);
            loginButton.setBounds(1200, 508, 50, 50);
            loginButton.setContentAreaFilled(false);
            loginButton.setBorder(null);
            loginButton.setFocusPainted(false);
            loginButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    LoginFrame loginFrame = new LoginFrame();    // 跳转登录界面
                    loginFrame.setVisible(true);
                    dispose();
                    System.out.println("登录");
                }
            });
            add(loginButton);
        }
    }

    // 添加所有组件（按钮）
    public void addComponent() {
        // 添加简单按钮
        JButton easyButton = new JButton("简单");
        easyButton.setBounds(490, 188, 300, 52);
        easyButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        easyButton.setForeground(Color.WHITE);
        easyButton.setBackground(new Color(85, 126, 248));
        easyButton.setBorder(null);
        easyButton.setFocusPainted(false);
        easyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                super.mouseClicked(e);
                gridSize = 3;
                ThemeFrame themeFrame = new ThemeFrame(userid);    // 跳转游戏界面
                themeFrame.setVisible(true);
                //隐藏当前窗口
                setVisible(false);
                System.out.println("简单");
            }
        });
        add(easyButton);

        // 添加中等按钮
        JButton mediumButton = new JButton("中等");
        mediumButton.setBounds(490, 250, 300, 52);
        mediumButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        mediumButton.setForeground(Color.WHITE);
        mediumButton.setBackground(new Color(85, 126, 248));
        mediumButton.setBorder(null);
        mediumButton.setFocusPainted(false);
        mediumButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                super.mouseClicked(e);
                gridSize = 4;
                ThemeFrame themeFrame = new ThemeFrame(userid);    // 跳转游戏界面
                themeFrame.setVisible(true);
                //隐藏当前窗口
                setVisible(false);
                System.out.println("中等");
            }
        });
        add(mediumButton);

        // 添加困难按钮
        JButton hardButton = new JButton("困难");
        hardButton.setBounds(490, 312, 300, 52);
        hardButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        hardButton.setForeground(Color.WHITE);
        hardButton.setBackground(new Color(85, 126, 248));
        hardButton.setBorder(null);
        hardButton.setFocusPainted(false);
        hardButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                super.mouseClicked(e);
                gridSize = 5;
                ThemeFrame themeFrame = new ThemeFrame(userid);    // 跳转游戏界面
                themeFrame.setVisible(true);
                //隐藏当前窗口
                setVisible(false);
                System.out.println("困难");
            }
        });
        add(hardButton);

        // 添加返回按钮
        JButton backButton = new JButton("返回");
        backButton.setBounds(490, 374, 300, 52);
        backButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(85, 126, 248));
        backButton.setBorder(null);
        backButton.setFocusPainted(false);
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                super.mouseClicked(e);
                TopFrame topFrame = new TopFrame(userid);    // 跳转游戏界面
                topFrame.setVisible(true);
                //隐藏当前窗口
                setVisible(false);
                System.out.println("返回");
            }
        });
        add(backButton);

        //添加排行榜按钮
        ImageIcon rankIcon = new ImageIcon("image/rank.png");
        JButton rankButton = new JButton(rankIcon);
        rankButton.setBounds(1200, 558, 50, 50);
        rankButton.setContentAreaFilled(false);
        rankButton.setBorder(null);
        rankButton.setFocusPainted(false);
        rankButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                RankFrame rankFrame = new RankFrame();    // 跳转排行榜界面
                rankFrame.setVisible(true);
                System.out.println("排行榜");
            }
        });
        add(rankButton);

        //添加帮助按钮
        ImageIcon helpIcon = new ImageIcon("image/help.png");
        JButton helpButton = new JButton(helpIcon);
        helpButton.setBounds(1200, 620, 50, 50);
        helpButton.setContentAreaFilled(false);
        helpButton.setBorder(null);
        helpButton.setFocusPainted(false);
        helpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //跳转浏览器指定网址
                try {
                    Desktop.getDesktop().browse(new java.net.URI(
                            "https://jingyan.baidu.com/article/fec7a1e5e1dc391190b4e7b1.html"));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                System.out.println("帮助");
            }
        });
        add(helpButton);
    }

    // 初始化窗口属性
    public void init() {
        setTitle("拼图游戏");
        setSize(1280, 720);
        getContentPane().setBackground(new Color(222, 240, 251));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                GetFileName.recoverFileName();
            }
        });
    }
}
