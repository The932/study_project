package com.itcast.puzzlegame.ui;

import com.itcast.puzzlegame.Database.DatabaseFunction;
import com.itcast.puzzlegame.function.DeletePicture;
import com.itcast.puzzlegame.function.GetFileName;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * 游戏主界面
 */
public class PuzzleFrame extends JFrame {
    private JRadioButton addNumInfo; // 是否添加数字提示
    private PictureCanvas canvas; // 拼图画布
    private PicturePreview preview; // 图片预览
    public static JTextField step; // 步数
    private JButton startButton; // 开始游戏按钮
    private JPanel leftPanel; // 左边的面板
    private int userid; // 用户ID

    // 构造函数
    public PuzzleFrame(int userid) {
        this.userid = userid;
        init(); // 初始化
        addComponent(); // 添加组件
        addPreviewImage(); // 添加预览图片与拼图图片
        addActionListener(); // 添加事件监听器
    }

    // 检查用户是否已登录并显示相应按钮
    public void isAuthenticated() {
        if (userid != 0) {
            System.out.println("用户ID: " + this.userid);
            // 添加新的按钮
            DatabaseFunction databaseFunction = new DatabaseFunction();
            String avatarPath = databaseFunction.getAvatar(userid);
            ImageIcon newButtonIcon = new ImageIcon("image/" + this.userid + ".jpg"); // 新按钮的图标
            JButton newButton = new JButton(newButtonIcon);
            newButton.setBounds(300, 10, 40, 40);
            newButton.setContentAreaFilled(false);
            newButton.setBorder(null);
            newButton.setFocusPainted(false);
            newButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    // 在这里添加新按钮的点击事件
                    UserFrame userFrame = new UserFrame(userid); // 跳转用户信息界面
                    userFrame.setVisible(true);
                    System.out.println("新按钮被点击");
                }
            });
            leftPanel.add(newButton);
        } else {
            // 添加登录按钮
            ImageIcon loginIco = new ImageIcon("image/DefaultAvatar2.png");
            JButton loginButton = new JButton(loginIco);
            loginButton.setBounds(600, 10, 50, 50);
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
            leftPanel.add(loginButton);
        }
    }

    // 添加事件监听器
    private void addActionListener() {
        // 添加开始游戏按钮的事件监听器
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 步数清零
                PictureCanvas.stepNum = 0;
                step.setText("步数：" + PictureCanvas.stepNum);
                // 开始游戏
                canvas.startGame();
            }
        });

        // 添加数字提示单选按钮的事件监听器
        addNumInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addNumInfo.isSelected()) {
                    // 重新加载，并添加数字提示
                    canvas.reloadPictureAddNumInfo();
                } else {
                    // 重新加载，并去除数字提示
                    canvas.reloadPictureRemoveNumInfo();
                }
            }
        });
    }

    // 添加预览图片与拼图图片
    private void addPreviewImage() {
        // 创建一个面板，包含拼图区与预览区
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2)); // 设置布局为表格布局
        // 创建拼图区
        canvas = new PictureCanvas(userid);
        // 图片预览区
        preview = new PicturePreview();
        // 把拼图区与图片预览区添加到面板
        panel.add(canvas, BorderLayout.WEST);
        panel.add(preview, BorderLayout.EAST);
        // 把面板显示在主界面中
        add(panel, BorderLayout.CENTER); // 添加到主界面的中间
    }

    // 添加组件
    private void addComponent() {
        // 创建一个主界面上方面板
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 2));

        // 创建左边的面板
        leftPanel = new JPanel();
        leftPanel.setBorder(new TitledBorder(" "));
        leftPanel.setBackground(new Color(173, 216, 230));
        leftPanel.setPreferredSize(new Dimension(200, 70));

        // 判断是否登录
        isAuthenticated();

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
        leftPanel.add(rankButton);

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
                // 跳转浏览器指定网址
                try {
                    Desktop.getDesktop().browse(new java.net.URI(
                            "https://jingyan.baidu.com/article/fec7a1e5e1dc391190b4e7b1.html"));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                System.out.println("帮助");
            }
        });
        leftPanel.add(helpButton);

        // 创建单选按钮数字提示
        addNumInfo = new JRadioButton("数字提示", false);
        addNumInfo.setBackground(new Color(173, 216, 230));
        addNumInfo.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        addNumInfo.setBorderPainted(false);
        addNumInfo.setFocusable(false);
        leftPanel.add(addNumInfo);

        // 创建开始游戏按钮
        startButton = new JButton("开始");
        startButton.setBackground(new Color(85, 126, 248));
        startButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        startButton.setBorderPainted(false);
        startButton.setFocusable(false);
        leftPanel.add(startButton);

        // 创建返回按钮
        JButton backButton = new JButton("返回");
        backButton.setBackground(new Color(85, 126, 248));
        backButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        backButton.setBorderPainted(false);
        backButton.setFocusable(false);
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                canvas.reloadPictureAddNumInfo();
                DeletePicture.deletePicture();
                GetFileName.RenameFileName(ThemeFrame.fileName);
                PictureCanvas.stepNum = 0;
                DifficultyFrame.gridSize = 0;
                ThemeFrame.fileName = "";
                ThemeFrame.picturePath = "";
                ThemeFrame.pictureID = "";
                DifficultyFrame difficultyFrame = new DifficultyFrame(userid);
                difficultyFrame.setVisible(true);
                dispose();
                System.out.println("返回");
            }
        });
        leftPanel.add(backButton);

        // 添加到主界面的左边
        topPanel.add(leftPanel, BorderLayout.WEST);

        // 创建右边的面板
        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(new TitledBorder(" "));
        rightPanel.setBackground(new Color(173, 216, 230)); // 设置背景颜色
        rightPanel.setPreferredSize(new Dimension(200, 70)); // 设置大小
        rightPanel.setLayout(new GridLayout(1, 2)); // 设置布局为表格布局

        // 添加组件 图片名称
        JTextField name = new JTextField("图片名称：" + ThemeFrame.fileName);
        name.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        name.setDisabledTextColor(Color.BLACK);
        name.setEnabled(false);

        // 添加组件 步数
        step = new JTextField("步数：" + PictureCanvas.stepNum);
        step.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        step.setDisabledTextColor(Color.BLACK);
        step.setEnabled(false);

        // 把组件添加到右边的面板
        rightPanel.add(name, BorderLayout.WEST);
        rightPanel.add(step, BorderLayout.EAST);

        topPanel.add(rightPanel, BorderLayout.EAST);

        // 添加上方面板到主界面
        this.add(topPanel, BorderLayout.NORTH);
    }

    // 初始化
    private void init() {
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
                DeletePicture.deletePicture();
                GetFileName.recoverFileName();
            }
        });
    }
}
