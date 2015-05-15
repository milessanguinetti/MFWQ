package Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Miles Sanguinetti on 5/15/15.
 */
public class Menu extends Frame{
    public int Selection;//the user's selection in the menu.
    /*
    0 = new game
    1 = continue
    2 = options
    3 = exit to desktop
     */

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
                    handleInput(0);
                }
                if(which == KeyEvent.VK_UP){
                    //case for moving a selection up
                    handleInput(1);
                }
                if(which == KeyEvent.VK_ENTER){
                    //case for executing a given selection
                    handleInput(2);
                }
            }
        });
    }

    //handle input from the keyadapter; check to ensure that we don't leave
    //the bounds of the integers that Selection can do something with.
    public void handleInput(int Input){
        if(Input == 0 && Selection < 3) //down case
            --Selection;
        if(Input == 1 && Selection > 0) //up case
            ++Selection;
        if(Input == 2){ //enter case
            if(Selection == 0) //new game case.
                return;
            if(Selection == 1) //continue case.
                return;
            if(Selection == 2) //Options case
                return;
            if(Selection == 3) //exit case
                currentGame.Exit();
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        repaint();
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
