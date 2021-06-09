import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameFrame extends JFrame{

        GameFrame(){
                this.add(new GamePanel());
                this.setTitle("Brennan's Pong Game");
                this.setBackground(Color.black);
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setVisible(true);
                this.setLocationRelativeTo(null);
                this.setResizable(false);
                this.pack();
        }
}
