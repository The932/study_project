package com.itcast.puzzlegame.ui;

import com.itcast.puzzlegame.Database.DatabaseFunction;
import com.itcast.puzzlegame.function.GetFileName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

public class PictureCanvas extends JPanel implements MouseListener {
    private DatabaseFunction databaseFunction = new DatabaseFunction(); // 数据库功能对象
    public static int stepNum = 0; // 步数
    public Rectangle nullCell; // 空方格
    public int userid; // 用户ID
    public int subImage = 540 / DifficultyFrame.gridSize; // 子图像的宽度和高度
    public boolean hasAddActionListener = false; // 是否已添加点击监听
    int cells[][] = new int[DifficultyFrame.gridSize][DifficultyFrame.gridSize]; // 方格位置数组

    // 构造函数
    public PictureCanvas(int userid) {
        this.userid = userid;
        setLayout(null); // 设置布局为空
        // 添加图片方格
        for (int i = 0; i < DifficultyFrame.gridSize; i++) {
            for (int j = 0; j < DifficultyFrame.gridSize; j++) {
                JLabel label = new JLabel(new ImageIcon(
                        "image/" + GetFileName.count + "_" + i + "_" + j + ".jpg"));
                label.setBounds(j * subImage + 30, i * subImage + 30, subImage, subImage);
                add(label);
            }
        }
        // 移除最后一个方格
        remove(getComponent(DifficultyFrame.gridSize * DifficultyFrame.gridSize - 1));
        nullCell = new Rectangle((DifficultyFrame.gridSize - 1) * subImage + 30,
                (DifficultyFrame.gridSize - 1) * subImage + 30, subImage, subImage); // 初始化空方格的位置和大小
    }

    // 重新加载图片并添加数字信息
    public void reloadPictureAddNumInfo() {
        for (int i = 0; i < DifficultyFrame.gridSize; i++) {
            for (int j = 0; j < DifficultyFrame.gridSize; j++) {
                if (i == DifficultyFrame.gridSize - 1 && j == DifficultyFrame.gridSize - 1) {
                    break;
                }
                int index = i * DifficultyFrame.gridSize + j;
                JLabel label = (JLabel) getComponent(index);
                label.setText(String.valueOf(index + 1)); // 设置数字
                label.setHorizontalTextPosition(JLabel.CENTER);
                label.setVerticalTextPosition(JLabel.CENTER);
            }
        }
    }

    // 重新加载图片并移除数字信息
    public void reloadPictureRemoveNumInfo() {
        for (int i = 0; i < DifficultyFrame.gridSize; i++) {
            for (int j = 0; j < DifficultyFrame.gridSize; j++) {
                if (i == DifficultyFrame.gridSize - 1 && j == DifficultyFrame.gridSize - 1) {
                    break;
                }
                int index = i * DifficultyFrame.gridSize + j;
                JLabel label = (JLabel) getComponent(index);
                label.setText(""); // 移除数字信息
            }
        }
    }

    // 开始游戏
    public void startGame() {
        // 添加点击监听
        if (!hasAddActionListener) {
            for (int i = 0; i < DifficultyFrame.gridSize; i++) {
                for (int j = 0; j < DifficultyFrame.gridSize; j++) {
                    if (i == DifficultyFrame.gridSize - 1 && j == DifficultyFrame.gridSize - 1) {
                        break;
                    }
                    JLabel label = (JLabel) getComponent(i * DifficultyFrame.gridSize + j);
                    label.addMouseListener(this);
                }
            }
            hasAddActionListener = true;
        }
        // 初始化位置数组
        ArrayList<Point> positions = new ArrayList<>();
        for (int i = 0; i < DifficultyFrame.gridSize; i++) {
            for (int j = 0; j < DifficultyFrame.gridSize; j++) {
                if (i == DifficultyFrame.gridSize - 1 && j == DifficultyFrame.gridSize - 1) {
                    break;
                }
                positions.add(new Point(j * subImage + 30, i * subImage + 30));
            }
        }
        // 打乱位置数组
        do {
            shufflePositions(positions);
        } while (!isSolvable(positions)); // 如果打乱后的数组不可解，则重新打乱

        // 初始化方格位置数组
        for (int i = 0; i < DifficultyFrame.gridSize; i++) {
            for (int j = 0; j < DifficultyFrame.gridSize; j++) {
                if (i == DifficultyFrame.gridSize - 1 && j == DifficultyFrame.gridSize - 1) {
                    break;
                }
                cells[i][j] = i * DifficultyFrame.gridSize + j;
            }
        }
        // 重新设置位置
        for (int i = 0; i < DifficultyFrame.gridSize; i++) {
            for (int j = 0; j < DifficultyFrame.gridSize; j++) {
                if (i == DifficultyFrame.gridSize - 1 && j == DifficultyFrame.gridSize - 1) {
                    break;
                }
                int index = i * DifficultyFrame.gridSize + j;
                Point point = positions.get(index);
                cells[i][j] = index;
                JLabel label = (JLabel) getComponent(index);
                label.setLocation(point);
            }
        }
    }

    // 打乱位置数组
    private void shufflePositions(ArrayList<Point> positions) {
        Collections.shuffle(positions);
    }

    // 判断打乱后的数组是否可解
    private boolean isSolvable(ArrayList<Point> positions) {
        int[] array = new int[DifficultyFrame.gridSize * DifficultyFrame.gridSize - 1];
        for (int i = 0; i < DifficultyFrame.gridSize * DifficultyFrame.gridSize - 1; i++) {
            array[i] = (positions.get(i).y - 30) / subImage * DifficultyFrame.gridSize +
                    (positions.get(i).x - 30) / subImage;
        }
        int inversions = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) inversions++;
            }
        }
        return inversions % 2 == 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // 未实现
    }

    @Override
    public void mousePressed(MouseEvent e) {
        JLabel clickedLabel = (JLabel) e.getSource(); // 获取被点击的图片
        Point clickedPoint = clickedLabel.getLocation(); // 获取被点击的图片的位置
        Point nullPoint = nullCell.getLocation(); // 获取空方格的位置

        // 判断被点击的图片和空方格是否相邻
        if (clickedPoint.x == nullPoint.x && clickedPoint.y - nullPoint.y == subImage) {
            clickedLabel.setLocation(clickedPoint.x, clickedPoint.y - subImage);
        } else if (clickedPoint.x == nullPoint.x && clickedPoint.y - nullPoint.y == -subImage) {
            clickedLabel.setLocation(clickedPoint.x, clickedPoint.y + subImage);
        } else if (clickedPoint.x - nullPoint.x == subImage && clickedPoint.y == nullPoint.y) {
            clickedLabel.setLocation(clickedPoint.x - subImage, clickedPoint.y);
        } else if (clickedPoint.x - nullPoint.x == -subImage && clickedPoint.y == nullPoint.y) {
            clickedLabel.setLocation(clickedPoint.x + subImage, clickedPoint.y);
        } else {
            return;
        }
        nullCell.setLocation(clickedPoint); // 更新空方格位置
        this.repaint();
        stepNum++;
        PuzzleFrame.step.setText("步数：" + stepNum);
        if (isFinish()) {
            if (userid != 0) {
                databaseFunction.uploadGameData(userid, stepNum, DifficultyFrame.gridSize);
            }
            JOptionPane.showMessageDialog(this,
                    "恭喜你完成拼图！\n您一共用了：" + stepNum + "步",
                    "拼图成功", JOptionPane.INFORMATION_MESSAGE);
            removeActionListener();
        }
    }

    // 判断是否完成拼图
    private boolean isFinish() {
        for (int i = 0; i < DifficultyFrame.gridSize; i++) {
            for (int j = 0; j < DifficultyFrame.gridSize; j++) {
                if (i == DifficultyFrame.gridSize - 1 && j == DifficultyFrame.gridSize - 1) {
                    break;
                }
                int x = getComponent(i * DifficultyFrame.gridSize + j).getX();
                int y = getComponent(i * DifficultyFrame.gridSize + j).getY();
                if ((y - 30) / subImage * DifficultyFrame.gridSize +
                        (x - 30) / subImage != i * DifficultyFrame.gridSize + j) {
                    return false;
                }
            }
        }
        return true;
    }

    // 取消方格的点击监听
    private void removeActionListener() {
        for (int i = 0; i < DifficultyFrame.gridSize; i++) {
            for (int j = 0; j < DifficultyFrame.gridSize; j++) {
                if (i == DifficultyFrame.gridSize - 1 && j == DifficultyFrame.gridSize - 1) {
                    break;
                }
                JLabel label = (JLabel) getComponent(i * DifficultyFrame.gridSize + j);
                label.removeMouseListener(this);
            }
        }
        hasAddActionListener = false; // 设置为未添加监听
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // 未实现
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // 未实现
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // 未实现
    }
}
