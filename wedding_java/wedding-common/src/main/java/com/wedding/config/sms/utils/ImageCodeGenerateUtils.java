package com.wedding.config.sms.utils;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * @Author: zhuqi
 * @Date: 2023/12/05/9:58
 * @Description: 图形验证码生成工具类
 */
@Component
public class ImageCodeGenerateUtils {
    /**
     * 生成验证码图片的宽度
     */
    private final int WIDTH = 100;
    /**
     * 生成验证码图片的高度
     */
    private final int HEIGHT = 30;
    /**
     * 字符样式
     */
    private final String[] FONT_NAMES = { "宋体", "楷体", "隶书", "微软雅黑" };
    /**
     * 定义验证码图片的背景颜色为白色
     */
    private final Color BACKGROUND_COLOR = new Color(255, 255, 255);
    /**
     * 生成随机
     */
    private final Random random = new Random();
    /**
     * 定义code字符
     */
    private final String CODES = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 记录随机字符串
     */
    private String text;

    /**
     * 获取一个随意颜色
     */
    private Color randomColor() {
        int red = random.nextInt(150);
        int green = random.nextInt(150);
        int blue = random.nextInt(150);
        return new Color(red, green, blue);
    }

    /**
     * 获取一个随机字体
     *
     */
    private Font randomFont() {
        String name = FONT_NAMES[random.nextInt(FONT_NAMES.length)];
        int style = random.nextInt(4);
        int size = random.nextInt(5) + 24;
        return new Font(name, style, size);
    }

    /**
     * 获取一个随机字符
     *
     */
    private char randomChar() {
        return CODES.charAt(random.nextInt(CODES.length()));
    }

    /**
     * 创建一个空白的BufferedImage对象
     *
     */
    private BufferedImage createImage() {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        //设置验证码图片的背景颜色
        g2.setColor(BACKGROUND_COLOR);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        return image;
    }

    public BufferedImage getImage() {
        BufferedImage image = createImage();
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            String s = randomChar() + "";
            sb.append(s);
            g2.setColor(randomColor());
            g2.setFont(randomFont());
            float x = i * WIDTH * 1.0f / 4;
            g2.drawString(s, x, HEIGHT - 8);
        }
        this.text = sb.toString();
        drawLine(image);
        return image;
    }

    /**
     * 绘制干扰线
     * @param image
     */
    private void drawLine(BufferedImage image) {
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        int num = 3;
        for (int i = 0; i < num; i++) {
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g2.setColor(randomColor());
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 获取验证码
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * 将验证码以JPEG图片输出到前端
     * @param image
     * @param out
     */
    public void output(BufferedImage image, OutputStream out) throws IOException {
        ImageIO.write(image, "JPEG", out);
    }
}
