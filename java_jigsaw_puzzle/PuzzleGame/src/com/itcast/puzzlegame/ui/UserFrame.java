package com.itcast.puzzlegame.ui;

import com.itcast.puzzlegame.Database.DatabaseFunction;
import com.itcast.puzzlegame.function.GetFileName;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

public class UserFrame extends JFrame {
    private int userid; // 用户ID
    private DatabaseFunction databaseFunction; // 数据库操作类
    private JLabel usernameLabel; // 用户名标签
    private JLabel userIdLabel; // 用户ID标签
    private JLabel avatarLabel; // 用户头像标签
    private JTable rankTable; // 排行榜表格
    private DefaultTableModel tableModel; // 表格模型

    public UserFrame(int userid) {
        this.userid = userid;
        databaseFunction = new DatabaseFunction();
        init(); // 初始化
        addComponent(); // 添加组件
        loadUserData(); // 加载用户数据
        loadUserRankData(); // 加载用户排行榜数据
    }

    // 添加组件
    public void addComponent() {
        // 用户头像
        String avatarPath = databaseFunction.getAvatar(userid);
        ImageIcon avatarIcon = new ImageIcon(avatarPath);
        avatarLabel = new JLabel(avatarIcon);
        avatarLabel.setBounds(10, 10, 50, 50);
        avatarLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    changeAvatar(); // 更改头像
                } catch (Exception ex) {
                    // 显示错误消息
                    JOptionPane.showMessageDialog(UserFrame.this, "更改头像失败：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(avatarLabel);

        // 用户信息标签
        usernameLabel = new JLabel("用户名: ");
        usernameLabel.setBounds(70, 10, 300, 30);
        usernameLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(usernameLabel);

        userIdLabel = new JLabel("用户ID: " + userid);
        userIdLabel.setBounds(70, 40, 300, 30);
        userIdLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(userIdLabel);

        // 用户排行榜表格
        String[] columnNames = {"排名", "步数", "难度", "时间"};
        tableModel = new DefaultTableModel(columnNames, 0);
        rankTable = new JTable(tableModel);
        rankTable.setFont(new Font("微软雅黑", Font.BOLD, 14));
        rankTable.setRowHeight(30);

        // 在单元格中居中对齐文本
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columnNames.length; i++) {
            rankTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // 设置表头颜色
        rankTable.getTableHeader().setBackground(new Color(85, 126, 248));
        rankTable.getTableHeader().setForeground(Color.WHITE);
        rankTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 14));
        rankTable.getTableHeader().setReorderingAllowed(false);
        rankTable.getTableHeader().setResizingAllowed(false);

        // 删除表格边框
        rankTable.setShowGrid(false);
        rankTable.setIntercellSpacing(new Dimension(0, 0));
        rankTable.setFocusable(false);

        // 删除JScrollPane的边框
        JScrollPane scrollPane = new JScrollPane(rankTable);
        scrollPane.setBounds(20, 100, 800, 400);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane);

        // 返回按钮
        JButton backButton = new JButton("返回");
        backButton.setBounds(20, 520, 100, 40);
        backButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(85, 126, 248));
        backButton.setBorder(null);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> dispose());
        add(backButton);

        // 退出登录按钮
        JButton logoutButton = new JButton("退出登录");
        logoutButton.setBounds(700, 520, 120, 40);
        logoutButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(85, 126, 248));
        logoutButton.setBorder(null);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> {
            // 关闭所有窗口
            for (Frame frame : Frame.getFrames()) {
                frame.dispose();
            }
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
        add(logoutButton);

        // 修改密码按钮
        JButton changePasswordButton = new JButton("修改密码");
        changePasswordButton.setBounds(130, 520, 100, 40);
        changePasswordButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        changePasswordButton.setForeground(Color.WHITE);
        changePasswordButton.setBackground(new Color(85, 126, 248));
        changePasswordButton.setBorder(null);
        changePasswordButton.setFocusPainted(false);
        changePasswordButton.addActionListener(e -> {
            try {
                showChangePasswordDialog(); // 显示修改密码对话框
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        add(changePasswordButton);
    }

    // 初始化
    public void init() {
        setTitle("用户信息");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
    }

    // 加载用户数据
    private void loadUserData() {
        String username = databaseFunction.getUsername(userid);
        usernameLabel.setText("用户名: " + username);
    }

    // 加载用户排行榜数据
    private void loadUserRankData() {
        List<Object[]> userRankData = databaseFunction.getUserRankData(userid);
        tableModel.setRowCount(0); // 清空现有数据
        int rank = 1;
        for (Object[] row : userRankData) {
            Object[] rowWithRank = new Object[row.length + 1];
            rowWithRank[0] = rank++;
            System.arraycopy(row, 0, rowWithRank, 1, row.length);
            tableModel.addRow(rowWithRank);
        }
    }

    // 修改头像的方法
    private void changeAvatar() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("选择新头像");

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (databaseFunction.updateAvatar(userid, selectedFile.getAbsolutePath())) {
                JOptionPane.showMessageDialog(this, "头像更新成功！重启程序后生效。");
                // 更新头像
                avatarLabel.setIcon(new ImageIcon("image/" + userid + ".jpg"));
                databaseFunction.getAvatar(userid);
                GetFileName.initializeFileName();
            } else {
                JOptionPane.showMessageDialog(this, "头像更新失败，请重试。");
            }
        }
    }

    // 显示修改密码对话框的方法
    private void showChangePasswordDialog() {
        JDialog dialog = new JDialog(this, "修改密码", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);

        JLabel oldPasswordLabel = new JLabel("旧密码:");
        oldPasswordLabel.setBounds(20, 20, 80, 30);
        oldPasswordLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        dialog.add(oldPasswordLabel);

        JPasswordField oldPasswordField = new JPasswordField();
        oldPasswordField.setBounds(100, 20, 160, 30);
        oldPasswordField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        dialog.add(oldPasswordField);

        JLabel newPasswordLabel = new JLabel("新密码:");
        newPasswordLabel.setBounds(20, 60, 80, 30);
        newPasswordLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        dialog.add(newPasswordLabel);

        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setBounds(100, 60, 160, 30);
        newPasswordField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        dialog.add(newPasswordField);

        JButton confirmButton = new JButton("确认");
        confirmButton.setBounds(30, 120, 80, 30);
        confirmButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setBackground(new Color(85, 126, 248));
        confirmButton.setBorder(null);
        confirmButton.setFocusPainted(false);
        confirmButton.addActionListener(e -> {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            if (databaseFunction.changePassword(userid, oldPassword, newPassword)) {
                JOptionPane.showMessageDialog(this, "密码修改成功");
                // 关闭所有窗口
                for (Frame frame : Frame.getFrames()) {
                    frame.dispose();
                }
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "旧密码错误");
            }
        });
        dialog.add(confirmButton);

        JButton cancelButton = new JButton("取消");
        cancelButton.setBounds(170, 120, 80, 30);
        cancelButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(85, 126, 248));
        cancelButton.setBorder(null);
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.add(cancelButton);

        dialog.setVisible(true);
    }
}
