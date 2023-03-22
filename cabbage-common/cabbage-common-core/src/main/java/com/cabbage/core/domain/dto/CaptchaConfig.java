package com.cabbage.core.domain.dto;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Component
public class CaptchaConfig {
    public static final String SESSION_KEY = "CAPTCHA"; // session的属性名
    public static final String RESPONSE_KEY_UUID = "CAPTCHA_UUID"; // session的属性名
    private static final int WIDTH = 80; // 验证码图片的宽度
    private static final int HEIGHT = 26; // 验证码图片的高度
    private static final int LENGTH = 4; // 验证码字符个数
    private static final int LINE_COUNT = 8; // 干扰线数量
    private static final int FONT_SIZE = 18; // 字体大小

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // 验证码字符集合

    private static final Random RANDOM = new Random();




    public void generateCaptcha(HttpSession session, HttpServletResponse response) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        // 生成随机字符
        StringBuilder captchaBuilder = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            captchaBuilder.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        String captcha = captchaBuilder.toString();

        // 绘制字符
        graphics.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        for (int i = 0; i < LENGTH; i++) {
            graphics.setColor(getRandomColor());
            graphics.drawString(String.valueOf(captcha.charAt(i)), i * (WIDTH / LENGTH) + 10, HEIGHT / 2 + 10);
        }

        // 绘制干扰线
        for (int i = 0; i < LINE_COUNT; i++) {
            graphics.setColor(getRandomColor());
            int x1 = RANDOM.nextInt(WIDTH);
            int y1 = RANDOM.nextInt(HEIGHT);
            int x2 = RANDOM.nextInt(WIDTH);
            int y2 = RANDOM.nextInt(HEIGHT);
            graphics.drawLine(x1, y1, x2, y2);
        }
        session.removeAttribute(SESSION_KEY);
        session.setAttribute(SESSION_KEY, captcha);
        graphics.dispose();
        Object uuid = session.getAttribute(RESPONSE_KEY_UUID);
        response.setHeader(CaptchaConfig.RESPONSE_KEY_UUID, uuid.toString());
        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return image;
    }

    private static Color getRandomColor() {
        int r = RANDOM.nextInt(256);
        int g = RANDOM.nextInt(256);
        int b = RANDOM.nextInt(256);
        return new Color(r, g, b);
    }





}
