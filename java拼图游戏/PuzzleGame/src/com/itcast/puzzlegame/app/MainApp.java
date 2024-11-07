package com.itcast.puzzlegame.app;

import com.itcast.puzzlegame.function.GetFileName;
import com.itcast.puzzlegame.ui.TopFrame;

/**
 * 启动器
 */
public class MainApp {
    public static void main(String[] args) {
        // 初始化文件名
        GetFileName.initializeFileName();
        // 创建主界面
        TopFrame topFrame = new TopFrame(0);
        // 显示界面
        topFrame.setVisible(true);
    }
}
