package com.itcast.puzzlegame.ui;

import javax.swing.*;
import java.awt.*;

/**
 * 图片预览面板
 */
public class PicturePreview extends JPanel {
    // 重写绘制方法
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 调用父类的绘制方法，确保面板的正确绘制
        // 设置图片路径
        String filename = ThemeFrame.picturePath + ThemeFrame.pictureID;
        // 获取图片
        ImageIcon icon = new ImageIcon(filename);
        Image image = icon.getImage();
        // 绘制图片到面板上，指定绘制位置和大小
        g.drawImage(image, 30, 30, 540, 540, this);
    }
}

