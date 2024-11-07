package com.itcast.puzzlegame.Database;

import java.sql.*;

public class DatabaseConnection {
    private static Connection connection;

    // 静态初始化块，用于加载数据库驱动和建立连接
    static {
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 建立数据库连接
            String url = "jdbc:mysql://localhost/PuzzleGame";
            String username = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            // 捕获异常并打印堆栈跟踪
            e.printStackTrace();
        }
    }

    // 获取数据库连接
    public static Connection getConnection() {
        return connection;
    }
}
