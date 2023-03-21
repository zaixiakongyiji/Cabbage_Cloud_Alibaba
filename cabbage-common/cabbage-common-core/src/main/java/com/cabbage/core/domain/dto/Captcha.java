package com.cabbage.core.domain.dto;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author yzj@1239694214@qq.com
 */
@Component
public class Captcha {
    public static final String SESSION_KEY = "CAPTCHA"; // session的属性名
    public static final String RESPONSE_KEY_UUID = "CAPTCHA_UUID"; // session的属性名
    private static final int WIDTH = 80; // 图片宽度
    private static final int HEIGHT = 26; // 图片高度
    private static final int LINESIZE = 8; // 图片干扰线数量
    private static final int NUMBER = 4; // 产生字符数量
    private static final int FONTSIZE = 18; // 图片文字大小
    private static final int R = 255;
    private static final int G = 255;
    private static final int B = 255;
    private static final int DEFAULT_FONT_STYLE = Font.CENTER_BASELINE;    //默认样式
    private static final String DEFAULT_FONT_FAMILY = Font.SANS_SERIF;    //默认字体

    /**
     * 随机数产生池
     */
    private static final char[] CHARSPOOL = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z'};

    private Random random = new Random();

    /**
     * 生成随机字符
     *
     * @return string
     */
    private char randomCode() {
        int length = CHARSPOOL.length;
        int num = random.nextInt(length);
        return CHARSPOOL[num];
    }

    /**
     * 设置随机颜色的样式
     *
     * @return color
     */
    private Color colorStyle() {
        int r = random.nextInt(R);
        int g = random.nextInt(G);
        int b = random.nextInt(B);
        return new Color(r, g, b);
    }

    /**
     * 设置字体的基本样式
     *
     * @return font
     */
    private Font fontStyle() {
        //为linux服务器使用
        System.setProperty("java.awt.headless", "true");
        return new Font(DEFAULT_FONT_FAMILY, DEFAULT_FONT_STYLE, FONTSIZE);
    }

    /**
     * 设置文字的颜色
     *
     * @return
     */
    private Color fontColorStyle() {
        int r = random.nextInt(100);
        int g = random.nextInt(100);
        int b = random.nextInt(100);
        return new Color(r, g, b);
    }

    /**
     * 绘制随机数
     *
     * @param g
     * @param i
     * @return char
     */
    private char drawRandomCode(Graphics g, int i) {
        g.setFont(fontStyle());
//        g.setColor(colorStyle());
        g.setColor(fontColorStyle());
        char randomCode = randomCode();
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(String.valueOf(randomCode), 13 * i, 16);
        return randomCode;
    }

    /**
     * 绘制干扰线
     *
     * @param g
     */
    private void drawLine(Graphics g) {
        int x = random.nextInt(WIDTH);
        int y = random.nextInt(HEIGHT);
        int x2 = random.nextInt(WIDTH / 2);
        int y2 = random.nextInt(HEIGHT / 2);
        g.drawLine(x, y, x2, y2);
    }

    /**
     * 生成验证码
     *
     * @param session
     * @param response
     */
    public void buildCaptcha(HttpSession session, HttpServletResponse response) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setFont(fontStyle());
        g.setColor(colorStyle());
        for (int i = 0; i < LINESIZE; i++) {
            drawLine(g);
        }
        StringBuilder code = new StringBuilder();
        for (int i = 1; i <= NUMBER; i++) {
            code.append(drawRandomCode(g, i));
        }
        session.removeAttribute(SESSION_KEY);
        session.setAttribute(SESSION_KEY, code.toString());
        g.dispose();
        Object uuid = session.getAttribute(RESPONSE_KEY_UUID);
        response.setHeader(Captcha.RESPONSE_KEY_UUID, uuid.toString());
        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
