package org.example;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BouncyBalls1 extends JPanel implements ActionListener {

    public static final Vector2D GRAVITY = new Vector2D(0, 1);
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 1080;
    public static final int CENTRE = WIDTH / 2;

    public static final int CIRCLE_SIZE = 930;
    public static final int CIRCLE_POS_X = 75;
    public static final int CIRCLE_POS_Y = 75;

    private final Ball CONTAINER = new Ball( new Vector2D(CENTRE, CENTRE), (double) CIRCLE_SIZE / 2);

    private final Ball ball1 = new Ball(new Vector2D(CENTRE, CENTRE - 155), new Vector2D(0, 0), 16);
    private final Ball ball2 = new Ball(new Vector2D(CENTRE - 135, CENTRE + 76), new Vector2D(0, 0), 16);
    private final Ball ball3 = new Ball(new Vector2D(CENTRE + 135, CENTRE + 76), new Vector2D(0, 0), 16);

    private final List<Ball> balls = List.of(ball1, ball2, ball3);

    public BouncyBalls1() {
        setBackground(Color.BLACK);
        Timer timer = new Timer(15, this);
        timer.start(); // Start the timer
    }

    private long lastFpsTime = System.currentTimeMillis();
    private int fps = 0;
    private int fpsCount = 0;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.WHITE);
        g2d.drawOval(CIRCLE_POS_X, CIRCLE_POS_Y, CIRCLE_SIZE, CIRCLE_SIZE);

        fpsCount += 1;
        if (System.currentTimeMillis() - lastFpsTime > 1000) {
            lastFpsTime = System.currentTimeMillis();
            fps = fpsCount;
            fpsCount = 0;
        }

        g2d.drawString("Fps: " + fps, WIDTH - 100, 20);

        for (Ball ball : balls) {
            Vector2D pos = ball.getPos();
            int radius = (int) ball.getRadius();
            g2d.fillOval((int) (pos.x() - radius), (int) (pos.y() - radius), radius * 2, radius * 2);

//            g2d.drawString("Pos: " + ball.getPos(), 20, 20);
//            g2d.drawString("Vel: " + ball.getVel(), 20, 40);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < balls.size(); i++) {

            Ball ball = balls.get(i);

            // Check for collisions
            Vector2D ballToContainer = ball.getPos().subtract(CONTAINER.getPos());
            double distToContainer = ballToContainer.magnitude() + ball.getRadius();
            if (distToContainer > CONTAINER.getRadius()) {
                Vector2D normal = ballToContainer.normalize();
                ball.setVel(ball.getVel().reflect(normal));
            }

            for (int j = i + 1; j < balls.size(); j++) {
                Ball ball2 = balls.get(j);
                double distance = ball2.getPos().subtract(ball.getPos()).magnitude();
                if (distance < ball.getRadius() + ball2.getRadius()) {
                    // Simplified collision handling for equal masses
                    Vector2D tempVel = ball.getVel();
                    ball.setVel(ball2.getVel());
                    ball2.setVel(tempVel);
                    ball.setRadius(ball.getRadius() * 1.1);
                    ball2.setRadius(ball2.getRadius() * 1.1);
                }
            }
        }

        for (Ball ball : balls) {
            // Update the position and velocity
            Vector2D curVel = ball.getVel();
            ball.setVel(curVel.add(GRAVITY));
            ball.setPos(ball.getPos().add(curVel).add(GRAVITY.scale(0.5)));
        }

//        System.out.println("Ball 2 vel: " + ball2.getVel());
//        System.out.println("Ball 3 vel: " + ball3.getVel());

        // Repaint the panel to update the oval's position
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT); // Preferred size for the panel
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BouncyBalls1 panel = new BouncyBalls1();
        frame.add(panel);
        frame.pack();

        Insets insets = frame.getInsets();
        int width = panel.getPreferredSize().width + insets.left + insets.right;
        int height = panel.getPreferredSize().height + insets.top + insets.bottom;

        // Set the frame size taking insets into account
        frame.setSize(width, height);
        frame.setVisible(true);
    }
}
