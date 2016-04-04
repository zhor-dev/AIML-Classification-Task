import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawNetwork extends JComponent {

    private static class Circle {
        final int x;
        final int y;
        final int r;
        final Color color;

        public Circle(int x, int y, int r, Color color) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.color = color;
        }
    }

    private final ArrayList<Circle> circles = new ArrayList<>();

    public void addCircle(int x, int y, int r) {
        addCircle(x, y, r, Color.BLACK);
    }

    public void addCircle(int x, int y, int r, Color color) {
        circles.add(new Circle(x, y, r, color));
        repaint();
    }

    public void clearCircles() {
        circles.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < circles.size(); ++i) {
            Circle circle = circles.get(i);
            if (circle != null) {
                ((Graphics2D) g).setStroke(new BasicStroke(5));
                g.setColor(circle.color);
                g.drawOval(circle.x, circle.y, circle.r, circle.r);
            }
        }
    }

    public static void main(String... args) {
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        testFrame.setSize(1000, 500);
        testFrame.setVisible(true);
        final DrawNetwork draw = new DrawNetwork();
        draw.setPreferredSize(new Dimension(1000, 500));
        testFrame.getContentPane().add(draw, BorderLayout.CENTER);
        ClassificationTask task = new ClassificationTask(150, 25);
        task.trainNetwork(500000, draw);
        testFrame.pack();
        testFrame.setVisible(true);
    }

}