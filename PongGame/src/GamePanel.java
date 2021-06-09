import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GamePanel extends JPanel implements Runnable{

    static final int GAME_WIDTH = 1200;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (5.0/9.0));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 18;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Paddle paddle1, paddle2;
    Ball ball;
    Random random;
    Graphics graphics;
    Score score;
    boolean running;

    GamePanel(){
        newPaddles();
        newBall();
        running = true;
        score = new Score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall(){
        random = new Random();
        ball = new Ball((GAME_WIDTH - BALL_DIAMETER) / 2, random.nextInt(GAME_HEIGHT - BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
    }

    public void newPaddles(){
        paddle1 = new Paddle(0, (GAME_HEIGHT - PADDLE_HEIGHT) / 2, PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT - PADDLE_HEIGHT) / 2, PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }

    public void paint(Graphics g){
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g){
        if(running) {
            paddle1.draw(g);
            paddle2.draw(g);
            ball.draw(g);
            score.draw(g);
        }
        else
            drawGameOver(g);
    }

    public void move(){
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollisions(){
        checkPaddleEdges();
        checkBallEdges();
        if(ball.intersects(paddle1) || ball.intersects(paddle2)){
            ball.xVelocity = -ball.xVelocity;
            if(ball.xVelocity < 0)
                ball.xVelocity--;
            else
                ball.xVelocity++;
            if(ball.yVelocity < 0)
                ball.yVelocity--;
            else
                ball.yVelocity++;
        }
    }

    public void checkBallEdges(){
        if(ball.x <= 0){
            score.player2++;
            newBall();
            newPaddles();
        }
        if(ball.x >= GAME_WIDTH - BALL_DIAMETER){
            score.player1++;
            newBall();
            newPaddles();
        }
        if((ball.y <= 0) || (ball.y >= GAME_HEIGHT - BALL_DIAMETER)){
            ball.yVelocity = -1 * ball.yVelocity;
        }
    }

    public void checkPaddleEdges(){
        if(paddle1.y <= 0){
            paddle1.y = 0;
        }
        if(paddle1.y >= GAME_HEIGHT - PADDLE_HEIGHT){
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }
        if(paddle2.y <= 0){
            paddle2.y = 0;
        }
        if(paddle2.y >= GAME_HEIGHT - PADDLE_HEIGHT){
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }
    }

    @Override
    public void run(){
        long lastTime = System.nanoTime();
        double ticks = 60.0;
        double ns = 1000000000.0 / ticks;
        double delta = 0;


        while(true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                repaint();
                delta--;
                if(running){
                    move();
                    checkCollisions();
                }
            }
            if ((score.player1 >= 7) || (score.player2 >= 7))
                running = false;
        }
    }

    public class AL extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
            if(e.getKeyCode() == KeyEvent.VK_R){
                if(!running) {
                    score.player1 = 0;
                    score.player2 = 0;
                    running = true;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }

    public void drawGameOver(Graphics g){
        score.draw(g);
        String restart = "Press R to Start New Game";
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 60));
        FontMetrics metrics = g.getFontMetrics();
        g.drawString("Game Over", (GAME_WIDTH - metrics.stringWidth("Game Over"))/2,  (GAME_HEIGHT/2) - 100);
        g.setFont(new Font("Arial", Font.PLAIN, 45));
        g.setColor(Color.white);
        metrics = g.getFontMetrics();
        g.drawString(restart, (GAME_WIDTH - metrics.stringWidth(restart)) / 2, (GAME_HEIGHT/2) + 120);
    }
}
