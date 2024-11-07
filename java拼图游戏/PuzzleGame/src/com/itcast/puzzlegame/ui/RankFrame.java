package com.itcast.puzzlegame.ui;

import com.itcast.puzzlegame.Database.DatabaseFunction;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * 排行榜界面类
 */
public class RankFrame extends JFrame {
    private JButton easyButton; // 简单难度按钮
    private JButton mediumButton; // 中等难度按钮
    private JButton hardButton; // 困难难度按钮
    private JTable rankTable; // 排行榜表格
    private DefaultTableModel tableModel; // 表格模型
    private DatabaseFunction databaseFunction; // 数据库操作对象

    /**
     * 构造函数
     */
    public RankFrame() {
        databaseFunction = new DatabaseFunction(); // 初始化数据库操作对象
        init(); // 初始化界面
        addComponents(); // 添加组件
        fetchAndDisplayData(3); // 默认显示难度3的数据
    }

    /**
     * 添加组件
     */
    private void addComponents() {
        // 添加排行榜标签
        JLabel rankLabel = new JLabel("排行榜");
        rankLabel.setBounds(20, 20, 100, 40);
        rankLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        add(rankLabel);

        // 添加排行榜表格
        String[] columnNames = {"排名", "玩家", "ID", "步数", "时间"};
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
        scrollPane.setBounds(10, 60, 800, 327);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane);

        // 添加简单难度按钮
        easyButton = new JButton("简单");
        easyButton.setBounds(20, 400, 100, 40);
        easyButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        easyButton.setForeground(Color.BLACK);
        easyButton.setBackground(new Color(85, 126, 248));
        easyButton.setBorder(null);
        easyButton.setFocusPainted(false);
        easyButton.addActionListener(e -> {
            easyButton.setForeground(Color.BLACK);
            mediumButton.setForeground(Color.WHITE);
            hardButton.setForeground(Color.WHITE);
            fetchAndDisplayData(3); // 显示难度3的数据
        });
        add(easyButton);

        // 添加中等难度按钮
        mediumButton = new JButton("中等");
        mediumButton.setBounds(140, 400, 100, 40);
        mediumButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        mediumButton.setForeground(Color.WHITE);
        mediumButton.setBackground(new Color(85, 126, 248));
        mediumButton.setBorder(null);
        mediumButton.setFocusPainted(false);
        mediumButton.addActionListener(e -> {
            mediumButton.setForeground(Color.BLACK);
            easyButton.setForeground(Color.WHITE);
            hardButton.setForeground(Color.WHITE);
            fetchAndDisplayData(4); // 显示难度4的数据
        });
        add(mediumButton);

        // 添加困难难度按钮
        hardButton = new JButton("困难");
        hardButton.setBounds(260, 400, 100, 40);
        hardButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        hardButton.setForeground(Color.WHITE);
        hardButton.setBackground(new Color(85, 126, 248));
        hardButton.setBorder(null);
        hardButton.setFocusPainted(false);
        hardButton.addActionListener(e -> {
            hardButton.setForeground(Color.BLACK);
            easyButton.setForeground(Color.WHITE);
            mediumButton.setForeground(Color.WHITE);
            fetchAndDisplayData(5); // 显示难度5的数据
        });
        add(hardButton);

        // 添加返回按钮
        JButton backButton = new JButton("返回");
        backButton.setBounds(20, 450, 100, 40);
        backButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(85, 126, 248));
        backButton.setBorder(null);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> dispose());
        add(backButton);
    }

    /**
     * 初始化界面
     */
    private void init() {
        setTitle("排行榜");
        setSize(835, 550);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
    }

    /**
     * 根据难度从数据库获取数据并显示在表格中
     * @param difficulty 难度级别
     */
    private void fetchAndDisplayData(int difficulty) {
        List<Object[]> data = databaseFunction.fetchData(difficulty); // 获取数据
        tableModel.setRowCount(0); // 清空现有数据
        int rank = 1;
        for (Object[] row : data) {
            Object[] rowData = new Object[]{rank++, row[0], row[1], row[2], row[3]};
            tableModel.addRow(rowData); // 添加新数据
        }
    }
}
