package com.itcast.puzzlegame.function;

import com.itcast.puzzlegame.ui.ThemeFrame;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class GetFileName {
    public static int count = 0;  // 图片总数计数器
    public static int countA = 0; // 动物图片计数器
    public static int countS = 0; // 风景图片计数器
    public static int countF = 0; // 人物图片计数器

    // 根据ID设置图片ID
    public static void GFileName(int ID) {
        switch (ID) {
            case 0 -> ThemeFrame.pictureID = "image/Animal" + countA + ".jpg";
            case 1 -> ThemeFrame.pictureID = "image/Scenery" + countS + ".jpg";
            case 2 -> ThemeFrame.pictureID = "image/Figure" + countF + ".jpg";
            default -> {
            }
        }
    }

    // 重命名文件名
    public static void RenameFileName(String oldName) {
        count++;
        switch (oldName) {
            case "Animal.jpg" -> {
                Path source = Paths.get("image/Animal" + countA + ".jpg");
                countA++;
                Path target = Paths.get("image/Animal" + countA + ".jpg");
                try {
                    Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("文件已重命名: " + target.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case "Scenery.jpg" -> {
                Path source1 = Paths.get("image/Scenery" + countS + ".jpg");
                countS++;
                Path target1 = Paths.get("image/Scenery" + countS + ".jpg");
                try {
                    Files.move(source1, target1, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("文件已重命名: " + target1.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case "Figure.jpg" -> {
                Path source2 = Paths.get("image/Figure" + countF + ".jpg");
                countF++;
                Path target2 = Paths.get("image/Figure" + countF + ".jpg");
                try {
                    Files.move(source2, target2, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("文件已重命名: " + target2.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            default -> {
            }
        }
    }

    // 恢复文件名
    public static void recoverFileName() {
        // 恢复动物图片文件名
        Path source = Paths.get("image/Animal" + countA + ".jpg");
        countA = 0;
        Path target = Paths.get("image/Animal" + countA + ".jpg");
        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("文件已重命名: " + target.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 恢复风景图片文件名
        Path source1 = Paths.get("image/Scenery" + countS + ".jpg");
        countS = 0;
        Path target1 = Paths.get("image/Scenery" + countS + ".jpg");
        try {
            Files.move(source1, target1, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("文件已重命名: " + target1.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // 恢复人物图片文件名
        Path source2 = Paths.get("image/Figure" + countF + ".jpg");
        countF = 0;
        Path target2 = Paths.get("image/Figure" + countF + ".jpg");
        try {
            Files.move(source2, target2, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("文件已重命名: " + target2.toString());
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    // 初始化文件名
    public static void initializeFileName() {
        Path dir = Paths.get("image/");
        // 检查目录是否存在并且是一个目录
        if (!Files.exists(dir) || !Files.isDirectory(dir)) {
            System.err.println("目录不存在或不是一个目录！");
            return;
        }

        // 定义计数器
        AtomicInteger animalCounter = new AtomicInteger(0);
        AtomicInteger sceneryCounter = new AtomicInteger(0);
        AtomicInteger figureCounter = new AtomicInteger(0);

        // 使用 Files.walk 方法遍历目录及其子目录中的所有文件
        try (Stream<Path> paths = Files.walk(dir)) {
            paths.filter(Files::isRegularFile).forEach(file -> {
                String fileName = file.getFileName().toString();

                // 删除文件名以数字开头的文件
                if (fileName.matches("^[0-9].*")) {
                    try {
                        Files.delete(file);
                        System.out.println("删除成功: " + file.toString());
                    } catch (IOException e) {
                        System.err.println("删除失败: " + file.toString());
                        e.printStackTrace();
                    }
                    return;
                }

                // 检查文件名是否为空并获取第一个字符
                if (fileName != null && !fileName.isEmpty()) {
                    char firstChar = fileName.charAt(0);
                    String newFileName = null;

                    // 根据第一个字符设置新的文件名
                    switch (firstChar) {
                        case 'A':
                            newFileName = "Animal" + animalCounter.getAndIncrement() + ".jpg";
                            break;
                        case 'S':
                            newFileName = "Scenery" + sceneryCounter.getAndIncrement() + ".jpg";
                            break;
                        case 'F':
                            newFileName = "Figure" + figureCounter.getAndIncrement() + ".jpg";
                            break;
                    }

                    // 如果新文件名不为空，则进行重命名
                    if (newFileName != null) {
                        Path newFile = file.resolveSibling(newFileName);
                        try {
                            Files.move(file, newFile, StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("重命名成功: " + fileName + " -> " + newFileName);
                        } catch (IOException e) {
                            System.err.println("重命名失败: " + fileName + " -> " + newFileName);
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (IOException e) {
            System.err.println("遍历目录时出错！");
            e.printStackTrace();
        }
    }
}
