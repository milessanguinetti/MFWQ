package Profile;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Miles Sanguinetti on 5/15/15.
 */
public abstract class Frame extends JPanel {
    protected Game currentGame;

    //default constructor
    public Frame(){
        setVisible(true);
        setFocusable(true); //make the jpanel focusable
        requestFocusInWindow(); //set focus to the window
        setBackground(Color.BLACK); //create a black background (PLACEHOLDER)
        setDoubleBuffered(true);
    }

    //constructor with a current game.
    public Frame(Game Current){
        setVisible(true);
        currentGame = Current;
        setFocusable(true); //make the jpanel focusable
        requestFocusInWindow();
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }
}
