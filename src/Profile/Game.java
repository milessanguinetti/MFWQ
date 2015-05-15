package Profile;

import javax.swing.*;
import java.awt.event.WindowEvent;

/**
 * Created by Miles Sanguinetti on 3/29/15.
 */
public class Game extends JFrame{
    //the player's user profile; public and static because only one will be used at a time;
    //it would be nonsensical to pass it around into virtually every function I call
    public static userProfile Player; //the game's player.

    public void Exit(){
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public Game(){
        Player = new userProfile();
        initializeInterface();
    }

    private void initializeInterface(){
        add(new Menu(this)); //add a menu object (a type of jpanel) to the jframe once it is opened.
        setSize(500, 400); //size is 500 by 400 pixels
        setTitle("Motherfucking Wizard Quest"); //title the application appropriately
        setDefaultCloseOperation(EXIT_ON_CLOSE); //close the application once it is commanded to close
        setLocationRelativeTo(null); //centers the window
    }


}
