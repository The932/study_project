package com.itcast.puzzlegame.ui;

import com.itcast.puzzlegame.Database.DatabaseFunction;
import com.itcast.puzzlegame.function.GetFileName;
import com.itcast.puzzlegame.function.ImageSplitter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * 选择图片主题界面
 */
public class ThemeFrame extends JFrame {
    public static String pictureID = ""; // 图片ID
    public static String picturePath = "";  // 图片路径
    public static String fileName = ""; // 文件名
    public int userid; // 用户ID

    /**
     * 构造函数
     * @param userid 用户ID
     */
    public ThemeFrame(int userid) {
        this.userid = userid;
        setLayout(null);    // 设置布局为空
        init(); // 初始化
        isAuthenticated(); // 检查用户是否登录并添加相应按钮
        addComponent(); // 添加组件
    }

    /**
     * 检查用户是否已登录并添加相应的按钮
     */
    public void isAuthenticated() {
        if (userid != 0) {
            // 用户已登录，显示头像按钮
            System.out.println("用户ID: " + this.userid);
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
                    UserFrame userFrame = new UserFrame(userid);    // 跳转用户信息界面
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
                    LoginFrame loginFrame = new LoginFrame();    // 跳转登录界面
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
        // 添加动物主题按钮
        JButton theme1Button = new JButton("动物");
        theme1Button.setBounds(490, 188, 300, 52);
        theme1Button.setFont(new Font("微软雅黑", Font.BOLD, 20));
        theme1Button.setForeground(Color.WHITE);
        theme1Button.setBackground(new Color(85, 126, 248));
        theme1Button.setBorder(null);
        theme1Button.setFocusPainted(false);
        theme1Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                GetFileName.GFileName(0); // 设置图片ID
                fileName = "Animal.jpg"; // 设置文件名
                ImageSplitter imageSplitter = new ImageSplitter(); // 图片分割
                imageSplitter.splitImage();
                PuzzleFrame puzzleFrame = new PuzzleFrame(userid); // 跳转游戏界面
                puzzleFrame.setVisible(true);
                setVisible(false); // 隐藏当前窗口
                System.out.println("动物");
            }
        });
        add(theme1Button);

        // 添加风景主题按钮
        JButton theme2Button = new JButton("风景");
        theme2Button.setBounds(490, 250, 300, 52);
        theme2Button.setFont(new Font("微软雅黑", Font.BOLD, 20));
        theme2Button.setForeground(Color.WHITE);
        theme2Button.setBackground(new Color(85, 126, 248));
        theme2Button.setBorder(null);
        theme2Button.setFocusPainted(false);
        theme2Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                GetFileName.GFileName(1); // 设置图片ID
                fileName = "Scenery.jpg"; // 设置文件名
                ImageSplitter imageSplitter = new ImageSplitter(); // 图片分割
                imageSplitter.splitImage();
                PuzzleFrame puzzleFrame = new PuzzleFrame(userid); // 跳转游戏界面
                puzzleFrame.setVisible(true);
                setVisible(false); // 隐藏当前窗口
                System.out.println("风景");
            }
        });
        add(theme2Button);

        // 添加人物主题按钮
        JButton theme3Button = new JButton("人物");
        theme3Button.setBounds(490, 312, 300, 52);
        theme3Button.setFont(new Font("微软雅黑", Font.BOLD, 20));
        theme3Button.setForeground(Color.WHITE);
        theme3Button.setBackground(new Color(85, 126, 248));
        theme3Button.setBorder(null);
        theme3Button.setFocusPainted(false);
        theme3Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                GetFileName.GFileName(2); // 设置图片ID
                fileName = "Figure.jpg"; // 设置文件名
                ImageSplitter imageSplitter = new ImageSplitter(); // 图片分割
                imageSplitter.splitImage();
                PuzzleFrame puzzleFrame = new PuzzleFrame(userid); // 跳转游戏界面
                puzzleFrame.setVisible(true);
                setVisible(false); // 隐藏当前窗口
                System.out.println("人物");
            }
        });
        add(theme3Button);

        // 添加自定义主题按钮
        JButton theme4Button = new JButton("自定义");
        theme4Button.setBounds(490, 374, 300, 52);
        theme4Button.setFont(new Font("微软雅黑", Font.BOLD, 20));
        theme4Button.setForeground(Color.WHITE);
        theme4Button.setBackground(new Color(85, 126, 248));
        theme4Button.setBorder(null);
        theme4Button.setFocusPainted(false);
        theme4Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
                    pictureID = selectedFile.getAbsolutePath(); // 设置图片ID
                    int lastSlash = Math.max(pictureID.lastIndexOf("\\"), pictureID.lastIndexOf("/"));
                    fileName = lastSlash != -1 ? pictureID.substring(lastSlash + 1) : pictureID; // 提取文件名
                    ImageSplitter imageSplitter = new ImageSplitter(); // 图片分割
                    imageSplitter.splitImage();
                    PuzzleFrame puzzleFrame = new PuzzleFrame(userid); // 跳转游戏界面
                    puzzleFrame.setVisible(true);
                    setVisible(false); // 隐藏当前窗口
                }
            }
        });
        add(theme4Button);

        // 添加返回按钮
        JButton backButton = new JButton("返回");
        backButton.setBounds(490, 436, 300, 52);
        backButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(85, 126, 248));
        backButton.setBorder(null);
        backButton.setFocusPainted(false);
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DifficultyFrame difficultyFrame = new DifficultyFrame(userid); // 跳转难度选择界面
                difficultyFrame.setVisible(true);
                setVisible(false); // 隐藏当前窗口
                System.out.println("返回");
            }
        });
        add(backButton);

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
                try {
                    Desktop.getDesktop().browse(new java.net.URI(
                            "https://jingyan.baidu.com/article/fec7a1e5e1dc391190b4e7b1.html")); // 跳转帮助页面
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
        getContentPane().setBackground(new Color(222, 240, 251));
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
