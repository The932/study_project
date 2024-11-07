package com.itcast.puzzlegame.Database;

import com.itcast.puzzlegame.function.ImageSplitter;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class DatabaseFunction {
    public int userID;
    private Connection connection;

    // 构造函数，初始化数据库连接
    public DatabaseFunction() {
        connectToDatabase();
    }

    // 连接到数据库
    private void connectToDatabase() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 验证用户
    public boolean validateUser(int userid, String password) {
        try {
            String sql = "SELECT * FROM users WHERE user_id=? AND password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userid);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            // 如果查询结果不为空，说明用户存在且密码正确
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 添加用户
    public boolean addUser(String username, String password, String avatarPath) {
        try {
            Random random = new Random();
            // 生成一个唯一的用户ID
            do {
                userID = random.nextInt(900000000) + 100000000;
            } while (doesUserIdExist(userID));

            String sql = "INSERT INTO users (user_id, username, password, avatar) VALUES (?, ?, ?, ?)";
            FileInputStream fis1 = new FileInputStream(avatarPath);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setBinaryStream(4, fis1, (int) new File(avatarPath).length());
            // 执行插入操作
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 检查用户ID是否存在
    private boolean doesUserIdExist(int userId) {
        try {
            String sql = "SELECT * FROM users WHERE user_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            // 如果查询结果不为空，说明用户ID存在
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 获取用户头像
    public String getAvatar(int userId) {
        try {
            String sql = "SELECT avatar FROM users WHERE user_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                File file = new File("image/" + userId + ".jpg");
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1];
                InputStream is = resultSet.getBinaryStream("avatar");
                // 从数据库读取头像数据并写入文件
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }
                fos.close();
                // 返回头像文件的绝对路径
                return file.getAbsolutePath();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 上传用户游戏数据
    public boolean uploadGameData(int userId, int steps_taken, int difficulty) {
        try {
            String sql = "INSERT INTO game_stats (user_id, steps_taken, difficulty) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, steps_taken);
            preparedStatement.setInt(3, difficulty);
            // 执行插入操作
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 查询难度为指定值的数据
    public List<Object[]> fetchData(int difficulty) {
        List<Object[]> data = new ArrayList<>();
        String query = "SELECT u.username, g.user_id, g.steps_taken, g.completion_time " +
                "FROM users u " +
                "JOIN game_stats g ON u.user_id = g.user_id " +
                "WHERE g.difficulty = ? " +
                "ORDER BY g.steps_taken ASC";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, difficulty);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    int userId = rs.getInt("user_id");
                    int steps = rs.getInt("steps_taken");
                    String time = rs.getString("completion_time");
                    // 将查询结果添加到列表中
                    data.add(new Object[]{username, userId, steps, time});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    // 获取用户名
    public String getUsername(int userId) {
        try {
            String sql = "SELECT username FROM users WHERE user_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // 返回查询到的用户名
                return resultSet.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 获取用户排行榜数据
    public List<Object[]> getUserRankData(int userId) {
        List<Object[]> rankData = new ArrayList<>();
        try {
            String sql = "SELECT steps_taken, difficulty, completion_time FROM game_stats WHERE user_id=? ORDER BY steps_taken ASC";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int steps = resultSet.getInt("steps_taken");
                int difficulty = resultSet.getInt("difficulty");
                String completionTime = resultSet.getString("completion_time");
                // 将查询结果添加到列表中
                rankData.add(new Object[]{steps, difficulty, completionTime});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rankData;
    }

    // 修改密码
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        try {
            // 验证旧密码
            if (validateUser(userId, oldPassword)) {
                String sql = "UPDATE users SET password=? WHERE user_id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, newPassword);
                preparedStatement.setInt(2, userId);
                // 执行更新操作
                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 更新用户头像
    public boolean updateAvatar(int userId, String avatarPath) {
        ImageSplitter imageSplitter = new ImageSplitter();
        // 缩放图像并获取新的头像路径
        String AvatarPath = imageSplitter.ImageZoom(userId, avatarPath);
        String sql = "UPDATE users SET avatar=? WHERE user_id=?";
        try (FileInputStream fis = new FileInputStream(AvatarPath);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setBinaryStream(1, fis, (int) new File(avatarPath).length());
            preparedStatement.setInt(2, userId);

            // 执行更新操作
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
