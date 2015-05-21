package Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Miles Sanguinetti on 5/15/15.
 */
public abstract class Frame extends JPanel {
    protected Game currentGame;

    //default constructor
    public Frame(){
        setFocusable(true); //make the jpanel focusable
        requestFocusInWindow(); //set focus to the window
        setBackground(Color.WHITE); //create a black background (PLACEHOLDER)
        setDoubleBuffered(true);

    }

    //constructor with a current game.
    public Frame(Game Current){
        currentGame = Current;
        setFocusable(true); //make the jpanel focusable
        requestFocusInWindow();
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

    }
}
