import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BreakoutGame extends JPanel implements ActionListener,
        KeyListener {
    Timer tm = new Timer(5, this);
    int x = 0, y = 0, paddleVelocityX = 0, paddleVelocityY = 0;
    static int widthOfPanel = 500;
    static int lengthOfPanel = 500;

    static int paddleWidth = 75;
    static int paddleLength = 50;

    private int circle_x, circle_y;
    private int circle_x_velocity, circle_y_velocity;
    private int diameter = 30;

    boolean keyPressed = false;

    public BreakoutGame() {

        circle_x = widthOfPanel / 2;
        circle_y = lengthOfPanel / 2;
        circle_x_velocity = 1;
        circle_y_velocity = 1;

        tm.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setBackground(Color.black);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.red);
        if (keyPressed == false) {
            g.fillRect(425, 425, paddleWidth, paddleLength);
        } else {
            g.fillRect(x + 425, y + 425, paddleWidth, paddleLength);
        }

        /*********** FOR CIRCLE **************/
        g.setColor(Color.BLUE);
        g.fillOval(circle_x, circle_y, diameter, diameter);
    }

    public static void main(String[] args) {
        BreakoutGame t = new BreakoutGame();

        JFrame jf = new JFrame();
        jf.setTitle("Breakout");
        jf.setSize(widthOfPanel, lengthOfPanel);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(t);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // gets the key value i think
        keyPressed = true;
        int c = e.getKeyCode();

        /** moves paddle left **/
        if (c == KeyEvent.VK_LEFT) {
            paddleVelocityX = -2;
            paddleVelocityY = 0;
        }
        /** moves paddle right **/
        if (c == KeyEvent.VK_RIGHT) {
            paddleVelocityX = 2;
            paddleVelocityY = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // do this to stop the square from moving
        paddleVelocityX = 0;
        paddleVelocityY = 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x = x + paddleVelocityX;
        y = y + paddleVelocityY;

        /************* FOR CIRCLE **************/
        if (circle_x + circle_x_velocity < 0
                || circle_x + circle_x_velocity + diameter > getWidth()) {
            circle_x_velocity = -circle_x_velocity;
        }

        if (circle_y + circle_y_velocity < 0
                || circle_y + circle_y_velocity + diameter > getHeight()) {
            circle_y_velocity = -circle_y_velocity;
        }
        if (circle_y + circle_y_velocity < 0
                || circle_y + circle_y_velocity + diameter > getHeight()) {
            circle_y_velocity = -circle_y_velocity;
        }

        if (collision()) {
            circle_y_velocity = -circle_y_velocity;
        }
        circle_x += circle_x_velocity;
        circle_y += circle_y_velocity;
        repaint();
    }

    public Rectangle getBoundsPaddle() {
        return new Rectangle(x + 425, y + 425, paddleWidth, paddleLength);
    }

    public Rectangle getBoundsCircle() {
        return new Rectangle(circle_x, circle_y, diameter, diameter);
    }

    private boolean collision() {
        return getBoundsCircle().intersects(getBoundsPaddle());
    }

    public int getUpperPartOfPaddle() {
        return y;
    }
    
    public void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
        System.exit(ABORT);
    }
}
