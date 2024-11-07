package com.itcast.puzzlegame.function;

import com.itcast.puzzlegame.ui.DifficultyFrame;
import com.itcast.puzzlegame.ui.SignupFrame;
import com.itcast.puzzlegame.ui.ThemeFrame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageSplitter {
    // 创建SignupFrame实例
    private SignupFrame signupFrame = new SignupFrame();
    // 日志记录器
    private static final Logger LOGGER = Logger.getLogger(ImageSplitter.class.getName());

    // 方法用于缩放用户头像图像
    public String ImageZoom(int userId, String filePath) {
        try {
            System.out.println(signupFrame.AvatarPath);
            // 读取源图像
            BufferedImage sourceImage = ImageIO.read(new File(filePath));

            if (sourceImage == null) {
                throw new IOException("无法读取图像，请检查文件路径和格式。");
            }

            int targetWidth = 50;  // 目标宽度
            int targetHeight = 50; // 目标高度

            // 调整图像大小
            Image scaledImage = sourceImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            // 保存调整后的图像
            File outputfile = new File("image/" + userId + ".jpg");
            ImageIO.write(resizedImage, "jpg", outputfile);
            return "image/" + userId + ".jpg";

        } catch (IOException e) {
            // 记录错误日志
            LOGGER.log(Level.SEVERE, "处理图像时发生错误：", e);
            System.err.println("处理图像时发生错误，请检查日志以获取详细信息。");
            return null;
        }
    }

    // 方法用于拆分图像
    public void splitImage() {
        try {
            // 读取源图像
            BufferedImage sourceImage = ImageIO.read(new File(ThemeFrame.picturePath + ThemeFrame.pictureID));

            if (sourceImage == null) {
                throw new IOException("无法读取图像，请检查文件路径和格式。");
            }

            // 设置目标宽度和高度为540*540
            int targetWidth = 540;
            int targetHeight = 540;

            // 调整图像大小
            Image scaledImage = sourceImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            // 计算每个子图像的宽度和高度
            int subImageWidth = targetWidth / DifficultyFrame.gridSize;
            int subImageHeight = targetHeight / DifficultyFrame.gridSize;

            // 创建一个数组来存储子图像
            BufferedImage[][] subImages = new BufferedImage[DifficultyFrame.gridSize][DifficultyFrame.gridSize];

            // 遍历九宫格的每个格子，创建子图像并存储到数组中
            for (int row = 0; row < DifficultyFrame.gridSize; row++) {
                for (int col = 0; col < DifficultyFrame.gridSize; col++) {
                    // 创建一个新的子图像
                    subImages[row][col] = new BufferedImage(subImageWidth, subImageHeight, resizedImage.getType());

                    // 获取子图像的图形对象
                    Graphics2D g = subImages[row][col].createGraphics();

                    // 绘制子图像
                    g.drawImage(resizedImage, 0, 0, subImageWidth, subImageHeight,
                            col * subImageWidth, row * subImageHeight,
                            (col + 1) * subImageWidth, (row + 1) * subImageHeight, null);

                    // 释放图形对象
                    g.dispose();

                    // 临时存储子图像到磁盘
                    File outputfile = new File("image/" + GetFileName.count + "_" + row + "_" + col + ".jpg");
                    ImageIO.write(subImages[row][col], "jpg", outputfile);
                }
            }

            System.out.println("图片调整分辨率并拆分完成并已临时存储。");

        } catch (IOException e) {
            // 记录错误日志
            LOGGER.log(Level.SEVERE, "处理图像时发生错误：", e);
            System.err.println("处理图像时发生错误，请检查日志以获取详细信息。");
        }
    }
}
