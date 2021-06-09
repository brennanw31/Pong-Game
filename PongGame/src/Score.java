import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Score extends Rectangle{

    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player1;
    int player2;

    Score(int GAME_WIDTH, int GAME_HEIGHT){
        this.GAME_WIDTH = GAME_WIDTH;
        this.GAME_HEIGHT = GAME_HEIGHT;
    }

    public void draw(Graphics g){
        g.setColor(Color.white);

        String player1Score = String.valueOf(player1/10) + String.valueOf(player1%10);
        String player2Score = String.valueOf(player2/10) + String.valueOf(player2%10);
        String totalScore = player1Score + "      " + player2Score;
        if((player1 >= 7) || (player2 >= 7)) {
            g.setFont(new Font("Times New Roman", Font.PLAIN, 45));
            FontMetrics metrics = g.getFontMetrics();
            g.drawString(player1Score, (GAME_WIDTH / 2) - 2 * metrics.stringWidth(player1Score), GAME_HEIGHT/2);
            g.drawString("---", (GAME_WIDTH - metrics.stringWidth("---")) / 2, GAME_HEIGHT/2);
            g.drawString(player2Score, (GAME_WIDTH / 2) + metrics.stringWidth(player2Score), GAME_HEIGHT/2);
            g.setColor(Color.green);
            String winner;
            if(player1 >= 7) {
                winner = "Player 1 wins!";
                g.setColor(Color.blue);
            }
            else {
                winner = "Player 2 wins!";
                g.setColor(Color.red);
            }
            g.drawString(winner, (GAME_WIDTH - metrics.stringWidth(winner)) / 2, (GAME_HEIGHT/2) + 60);
        }
        else{
            g.setFont(new Font("Times New Roman", Font.PLAIN, 35));
            FontMetrics metrics = g.getFontMetrics();
            g.drawString(totalScore, (GAME_WIDTH - metrics.stringWidth(totalScore)) / 2, 40);
            g.setColor(Color.GRAY);
            g.drawLine(GAME_WIDTH/2, 0, GAME_WIDTH/2, GAME_HEIGHT);
        }
    }
}
