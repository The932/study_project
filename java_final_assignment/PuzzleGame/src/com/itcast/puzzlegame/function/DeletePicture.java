package com.itcast.puzzlegame.function;

import com.itcast.puzzlegame.ui.DifficultyFrame;

import java.io.IOException;
import java.nio.file.*;

public class DeletePicture {

    // 删除图片方法
    public static void deletePicture() {
        // 释放图片资源
        Path directoryPath = Paths.get("image/"); // 定义图片所在目录的路径
        for (int i = 0; i < DifficultyFrame.gridSize; i++) { // 遍历行
            for (int j = 0; j < DifficultyFrame.gridSize; j++) { // 遍历列
                // 构造要删除的文件名
                String fileNameToDelete = GetFileName.count + "_" + i + "_" + j + ".jpg";
                Path filePath = directoryPath.resolve(fileNameToDelete); // 获取文件的完整路径
                try {
                    // 删除文件
                    Files.delete(filePath);
                    System.out.println("文件已删除: " + filePath.toString());
                } catch (NoSuchFileException ex) {
                    // 捕获文件不存在的异常
                    System.err.println("文件不存在: " + filePath.toString());
                } catch (DirectoryNotEmptyException ex) {
                    // 捕获目录非空的异常
                    System.err.println("目录不是空的: " + filePath.toString());
                } catch (IOException ex) {
                    // 捕获其他IO异常
                    System.err.println("无法删除文件: " + filePath.toString());
                    ex.printStackTrace();
                }
            }
        }
    }
}
