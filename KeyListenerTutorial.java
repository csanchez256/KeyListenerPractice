import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class KeyListenerTutorial extends JPanel implements ActionListener, KeyListener {
    Timer tm = new Timer(5,this);
    int x = 0, y = 0, velX = 0, velY = 0;
    static int widthOfPanel = 500;
    static int lengthOfPanel = 500;
    
    static int paddleWidth = 75;
    static int paddleLength = 50;
    
    private int circle_x, circle_y;
    private int circle_xDir, circle_yDir;
    private int diameter = 30;
    
    boolean keyPressed = false;
    
    public KeyListenerTutorial(){
        
        circle_x = widthOfPanel/2;
        circle_y = lengthOfPanel/2;
        circle_xDir = 1;
        circle_yDir = 2;
        
        tm.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setBackground(Color.black);
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.red);
        if(keyPressed == false){
            g.fillRect(425, 425, paddleWidth, paddleLength);
        }
        else { g.fillRect(x+425, y+425, paddleWidth, paddleLength);}
        
        
        /***********FOR CIRCLE **************/
        g.setColor(Color.BLUE);
        g.fillOval(circle_x, circle_y, diameter, diameter);
    }
    public static void main(String[] args){
        KeyListenerTutorial t = new KeyListenerTutorial();
        
        JFrame jf = new JFrame();
        jf.setTitle("Breakout");
        jf.setSize(widthOfPanel,lengthOfPanel);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(t);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        //gets the key value i think
        keyPressed = true;
        int c = e.getKeyCode();
        
        if(c == KeyEvent.VK_LEFT){
            velX = -1;
            velY = 0;
        }
//        if(c == KeyEvent.VK_UP){
//            velX = 0;
//            velY = -1;
//        }
        if (c == KeyEvent.VK_RIGHT){
            velX = 1;
            velY = 0;
        }
//        if(c == KeyEvent.VK_DOWN){
//            velX = 0;
//            velY = 1;
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        //do this to stop the square from moving
       velX = 0;
       velY = 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        x = x + velX;
        y = y + velY;
        
        
        /*************FOR CIRCLE**************/
        if(circle_x + circle_xDir < 0 ||
                circle_x + circle_xDir + diameter > getWidth()) {
                 circle_xDir = -circle_xDir;
             }

        if(circle_y + circle_yDir < 0 ||
                circle_y + circle_yDir + diameter > getHeight()) {
                 circle_yDir = -circle_yDir;
             }
             circle_x += circle_xDir;
             circle_y += circle_yDir;
        if(collision()){System.out.println("Collision");}     
        repaint();
    }
    
    public Rectangle getBoundsPaddle(){
        System.out.println("x: "+x+"y: "+y);
        return new Rectangle(x,y, paddleWidth,paddleLength);
    }
    
    public Rectangle getBoundsCircle(){
        return new Rectangle(circle_x,circle_y, diameter,diameter);
    }
    
    private boolean collision(){
        return getBoundsPaddle().intersects(getBoundsCircle());
    }
}
