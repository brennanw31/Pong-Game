import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Ball extends Rectangle{

    Random random;
    int xVelocity;
    int yVelocity;

    Ball(int x, int y, int width, int height){
        super(x,y,width,height);
        random = new Random();
        int randomX = random.nextInt(2);
        int randomY = random.nextInt(2);
        if(randomX == 0)
            randomX--;
        setXDirection(2 * randomX);
        if(randomY == 0)
            randomY--;
        setYDirection(2 * randomY);
    }

    public void setXDirection(int randomX){
        xVelocity = randomX;
    }

    public void setYDirection(int randomY){
        yVelocity = randomY;
    }

    public void move(){
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillOval(x, y, width, height);
    }
}
