package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class House extends JPanel implements ActionListener {

    // Для анімації масштабування
    private double scale = 1;
    private double delta = 0.01;

    private double tx = 0;
    private double ty = -1;
    private double deltaX = 1;
    private int radius = 170;

    Timer timer;
    private static int maxWidth;
    private static int maxHeight;
    public House() {
        // Таймер генеруватиме подію що 10 мс
        timer = new Timer(10, this);
        timer.start();
    }

    private void prepareBackground(Graphics2D g2d){
        // Встановлюємо кольори
        g2d.setBackground(new Color(0, 0, 128));
        g2d.clearRect(0, 0, maxWidth+1, maxHeight+1);
        // Встановлюємо параметри рендерингу
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

    private void drawHouse(Graphics2D g2d){
        g2d.setPaint(new Color(128, 0, 0));
        g2d.fillRect(-120, 0, 250, 100);
    }

    private void  drawRoof(Graphics2D g2d){
        double points[][] = { {-120.0, 0.0}, {-120.0, -5.0},
                {-25.0, -40.0},
                {130.0, -5.0}, {130.0, 0.0}};
        GeneralPath roof = new GeneralPath();
        roof.moveTo(points[0][0], points[0][1]);
        for (int k = 1; k < points.length; k++)
            roof.lineTo(points[k][0], points[k][1]);
        roof.closePath();
        GradientPaint gp = new GradientPaint(25, 25,
                Color.LIGHT_GRAY, 20, 20, Color.BLACK, true);
        g2d.setPaint(gp);
        g2d.fill(roof);
    }

    private void drawStars(Graphics2D g2d){
        int x = -105;
        int y = -100;
        int a = 10;
        int nx = 16 * a;
        int ny = a;
        int starsCount = 4;
        for (int i = 1; i <= starsCount; i++) {
            if(i % 3 == 0){
                y += a + ny;
                x = -135;
            }
            g2d.setPaint(Color.YELLOW);
            g2d.fillRect(x, y, a, a);
            x += a + nx;
        }
    }

    private void drawWindows(Graphics2D g2d){
        int x = -90;
        int y = 30;
        int a = 30;
        int nx = 45;
        int winCount = 2;
        for (int i = 0; i < winCount; i++) {
            g2d.setPaint(Color.YELLOW);
            g2d.fillRect(x, y, a, a);
            x += a + nx;
        }
    }

    private void drawFrame(Graphics2D g2d){
        BasicStroke bs2 = new BasicStroke(5, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER);
        g2d.setStroke(bs2);
        g2d.setPaint(Color.CYAN);
        g2d.drawRect(0, 0, 732, 560);
    }

        public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        prepareBackground(g2d);
            drawFrame(g2d);
        g2d.translate(maxWidth/2, maxHeight/2);
        //g2d.translate(tx, ty);
            //g2d.scale(scale, 0.99);

        drawRoof(g2d);
        drawHouse(g2d);
        drawStars(g2d);
        drawWindows(g2d);
    }

        public static void main(String[] args) {
        JFrame frame = new JFrame("Lab2_Arnautova");
        frame.add(new House());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Dimension size = frame.getSize();
        Insets insets = frame.getInsets();
        maxWidth =  size.width - insets.left - insets.right - 1;
        maxHeight =  size.height - insets.top - insets.bottom - 1;



    }
        // Цей метод буде викликано щоразу, як спрацює таймер
    public void actionPerformed(ActionEvent e) {
        if ( scale < 0.01 ) {
            delta = -delta;
        } else if (scale > 0.99) {
            delta = -delta;
        }
        // movement
        double radiusInSquare = Math.pow(radius, 2);
        if (tx <= 0 && ty > 0){ //down-left
            tx -= deltaX;
            ty = Math.abs(Math.sqrt(radiusInSquare - Math.pow(tx, 2)));
        }else if(tx >= 0 && ty < 0){ //up-right
            tx += deltaX;
            ty = (-1) * Math.abs(Math.sqrt(radiusInSquare - Math.pow(tx, 2)));
        }else if(tx < 0 && ty <= 0){//left-up
            tx += deltaX;
            ty = (-1)*Math.abs(Math.sqrt(radiusInSquare - Math.pow(tx, 2)));
        }else if(tx > 0 && ty >= 0){ //right-down
            tx -= deltaX;
            ty = Math.abs(Math.sqrt(radiusInSquare - Math.pow(tx, 2)));
        }

        scale += delta;
        repaint();
    }
}