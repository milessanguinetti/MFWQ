package Profile;

import com.sun.org.apache.bcel.internal.generic.NEW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Miles Sanguinetti on 5/15/15.
 */
public class Menu extends Frame{
    private int Selection;//the user's selection in the menu.
    private JLabel [] Labels = new JLabel[4]; //an array of labels corresponding to selection
    /*
    0 = new game
    1 = continue
    2 = options
    3 = exit to desktop
     */
    Font Bold; //a general bold font to use for labels
    Font Plain; //a general plain font to use for labels

    //default constructor; ideally, do not use.
    public Menu(){}

    public Menu(Game current){
        super(current);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                //only act on released keys
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                int which = keyEvent.getKeyCode();
                //which key was typed
                if(which == KeyEvent.VK_DOWN){
                    handleInput(1);
                }
                if(which == KeyEvent.VK_UP){
                    //case for moving a selection up
                    handleInput(0);
                }
                if(which == KeyEvent.VK_ENTER){
                    //case for executing a given selection
                    handleInput(2);
                }
            }
        });
        setLayout(new GridBagLayout()); //set layout and get a constraints var
        GridBagConstraints Constraints = new GridBagConstraints(); //for formatting
        Constraints.gridwidth = 0; //set width to 0 for a horizontal layout.
        Constraints.insets = new Insets(30, 0, 0, 0); //top padding

        Labels[0] = new JLabel("New Game");
        Labels[1] = new JLabel("Continue");
        Labels[2] = new JLabel("Options");
        Labels[3] = new JLabel("Exit to Desktop");
        Bold = new Font("Verdana", Font.BOLD, 30);
        Plain = new Font("Verdana", Font.PLAIN, 30);
        Labels[0].setFont(Bold);
        Labels[1].setFont(Plain);
        Labels[2].setFont(Plain);
        Labels[3].setFont(Plain);
        for(int i = 0; i < 4; ++i)
            add(Labels[i], Constraints);
    }

    //handle input from the keyadapter; check to ensure that we don't leave
    //the bounds of the integers that Selection can do something with.
    public void handleInput(int Input){
        if(Input == 0 && Selection > 0) { //down case
            Labels[Selection].setFont(Plain); //set the old selection to plain
            --Selection; //decrement selection
            Labels[Selection].setFont(Bold); //set the new selection to bold
        }
        if(Input == 1 && Selection < 3) { //up case
            Labels[Selection].setFont(Plain); //set the old selection to plain
            ++Selection; //increment selection
            Labels[Selection].setFont(Bold); //set the new selection to bold
        }
        if(Input == 2){ //enter case
            if(Selection == 0) //new game case.
                currentGame.Test();
            if(Selection == 1) //continue case.
                return;
            if(Selection == 2) //Options case
                return;
            if(Selection == 3) //exit case
                currentGame.Exit();
        }
    }

    //gets an image corresponding to the highlighted user selection.
    /*public Image getImage(){

    }*/

    //redraws the menu
    public void paint(Graphics toDraw){
        super.paint(toDraw);
        //((Graphics2D)toDraw).drawImage(IMAGE, x, y, this);
        Toolkit.getDefaultToolkit().sync();
        toDraw.dispose();
    }
}
