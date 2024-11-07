package com.itcast.puzzlegame.ui;

import com.itcast.puzzlegame.Database.DatabaseFunction;
import com.itcast.puzzlegame.function.GetFileName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 主界面
 */
public class TopFrame extends JFrame {
    public int userid; // 用户ID

    /**
     * 构造函数
     * @param userid 用户ID
     */
    public TopFrame(int userid) {
        this.userid = userid;
        init(); // 初始化
        isAuthenticated(); // 检查用户是否登录并添加相应按钮
        addComponent(); // 添加组件
    }

    /**
     * 检查用户是否已登录并添加相应的按钮
     */
    public void isAuthenticated() {
        if (userid != 0) {
            System.out.println("用户ID: " + this.userid);
            // 用户已登录，显示头像按钮
            DatabaseFunction databaseFunction = new DatabaseFunction();
            String avatarPath = databaseFunction.getAvatar(userid); // 获取用户头像路径
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
                    UserFrame userFrame = new UserFrame(userid); // 跳转用户信息界面
                    userFrame.setVisible(true);
                    System.out.println("新按钮被点击");
                }
            });
            add(newButton);
        } else {
            // 用户未登录，显示默认头像并添加登录功能
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
                    LoginFrame loginFrame = new LoginFrame(); // 跳转登录界面
                    loginFrame.setVisible(true);
                    dispose();
                    System.out.println("登录");
                }
            });
            add(loginButton);
        }
    }

    /**
     * 添加组件
     */
    private void addComponent() {
        // 添加开始游戏按钮
        JButton startButton = new JButton("开始游戏");
        startButton.setBounds(490, 488, 300, 52);
        startButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(85, 126, 248));
        startButton.setBorder(null);
        startButton.setFocusPainted(false);
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DifficultyFrame difficultyFrame = new DifficultyFrame(userid); // 跳转游戏界面
                difficultyFrame.setVisible(true);
                setVisible(false); // 隐藏当前窗口
                System.out.println("开始游戏");
            }
        });
        add(startButton);

        // 添加退出游戏按钮
        JButton exitButton = new JButton("退出游戏");
        exitButton.setBounds(490, 550, 300, 52);
        exitButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(85, 126, 248));
        exitButton.setBorder(null);
        exitButton.setFocusPainted(false);
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // 弹窗提示
                int result = JOptionPane.showConfirmDialog(null, "确定要退出吗？", "退出", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    GetFileName.recoverFileName(); // 恢复文件名
                    System.exit(0); // 退出程序
                } else if (result == JOptionPane.NO_OPTION) {
                    System.out.println("取消退出游戏");
                }
            }
        });
        add(exitButton);

        // 添加排行榜按钮
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
                RankFrame rankFrame = new RankFrame(); // 跳转排行榜界面
                rankFrame.setVisible(true);
                System.out.println("排行榜");
            }
        });
        add(rankButton);

        // 添加帮助按钮
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
                // 打开浏览器跳转到帮助页面
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

    /**
     * 初始化
     */
    private void init() {
        setTitle("拼图游戏");
        setSize(1280, 720);
        setContentPane(new JLabel(new ImageIcon("image/top.png")));
        setResizable(false);
        setLocationRelativeTo(null); // 窗口居中
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
