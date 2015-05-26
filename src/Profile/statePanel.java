package Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Miles Sanguinetti on 5/15/15.
 */
//state panels are JPanels that represent one of the game's four states:
//the main menu, the world, the battle screen and the in-game menu.
public abstract class statePanel extends JPanel implements ActionListener{
    protected Game currentGame;
    Timer timer; //a timer to avoid EDT issues when getting input
    private int input;
    /*
    0 = back
    1 = up
    2 = down
    3 = enter
     */

    //default constructor
    public statePanel(){
        setVisible(true);
        setFocusable(true); //make the jpanel focusable
        requestFocusInWindow(); //set focus to the window
        setBackground(Color.WHITE); //create a black background (PLACEHOLDER)
        setDoubleBuffered(true);

        timer = new Timer(10, this);
        timer.start();
    }

    //constructor with a current game.
    public statePanel(Game Current){
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    setInput(1);
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    setInput(2);
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT
                        || e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                    setInput(0);
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT
                        || e.getKeyCode() == KeyEvent.VK_ENTER){
                    setInput(3);
                }
            }
        });
        setVisible(true);
        currentGame = Current;
        setFocusable(true); //make the jpanel focusable
        requestFocusInWindow();
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        timer = new Timer(10, this);
        timer.start();
    }

    public void setInput(int toSet){
        input = toSet;
    }

    //gets a single key of input; based on code written by
    //stackoverflow user Garrett Hall
    public int getInput(){
        return input;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        repaint();
    }
}
