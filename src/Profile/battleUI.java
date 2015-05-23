package Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Miles Sanguinetti on 5/21/15.
 */
public class battleUI extends JPanel{
    JLabel [] Left; //generally selection and user-input driven labels
    JLabel [] Right; //generally information-based, descriptive labels
    Font Bold; //a font for highlighting a given option
    Font Plain; //a font for non-highlighted options
    private static int input;
    /*
    0 = back
    1 = up
    2 = down
    3 = enter
     */

    public static void setInput(int toSet){
        input = toSet;
    }

    public battleUI(){
        setSize(1000, 200);
        setBackground(Color.BLUE);
        setFocusable(true); //make the jpanel focusable
        requestFocusInWindow(); //set focus to the window
        Bold = new Font("Verdana", Font.BOLD, 12); //set fonts
        Plain = new Font("Verdana", Font.PLAIN, 12);
        for(int i = 0; i < 4; ++i){
            Left[i] = new JLabel();
            Left[i].setFont(Plain);
            Right[i] = new JLabel();
            Right[i].setFont(Plain);
        } //allocate memory for all labels
    }

    //gets a single key of input; based on code written by
    //stackoverflow user Garrett Hall
    public int getInput(){
        int Which = 0;
        final CountDownLatch latch = new CountDownLatch(1);
        KeyEventDispatcher dispatcher = new KeyEventDispatcher() {
            //new key event dispatcher
            public boolean dispatchKeyEvent(KeyEvent whichKey) {
                if (whichKey.getKeyCode() == KeyEvent.VK_UP){
                    latch.countDown();
                    battleUI.setInput(1);
                }
                else if (whichKey.getKeyCode() == KeyEvent.VK_DOWN){
                    latch.countDown();
                    battleUI.setInput(2);
                }
                else if (whichKey.getKeyCode() == KeyEvent.VK_LEFT
                        || whichKey.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                    latch.countDown();
                    battleUI.setInput(0);
                }
                else if (whichKey.getKeyCode() == KeyEvent.VK_RIGHT
                        || whichKey.getKeyCode() == KeyEvent.VK_ENTER){
                    latch.countDown();
                    battleUI.setInput(3);
                }
                return false;
            }
        };
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(dispatcher);
        try {latch.await();}  // current thread waits here until countDown() is called
        catch (InterruptedException Caught){}
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(dispatcher);

        return input;
    }

    public void setTextFocus(int i){
        for(int j = 0; i < 4; ++i)
            Left[j].setFont(Plain);
        Left[i].setFont(Bold);
    }

    //prints to the left at just one index specified as an argument.
    public void printLeft(String S1, int Index){
        if(Left[Index] != null){
            if(!Left[Index].getText().equals(""))
                return; //don't overwrite extant text.
        }
        Left[Index].setText(S1);
    }

    //Print functions to left labels
    public void printLeft(String S1){
        Left[0].setText(S1);
        Left[1].setText("");
        Left[2].setText("");
        Left[3].setText("");
    }

    public void printLeft(String S1, String S2){
        Left[0].setText(S1);
        Left[1].setText(S2);
        Left[2].setText("");
        Left[3].setText("");
    }

    public void printLeft(String S1, String S2, String S3){
        Left[0].setText(S1);
        Left[1].setText(S2);
        Left[2].setText(S3);
        Left[3].setText("");
    }

    public void printLeft(String S1, String S2, String S3, String S4){
        Left[0].setText(S1);
        Left[1].setText(S2);
        Left[2].setText(S3);
        Left[3].setText(S4);
    }

    //Print functions to right labels
    public void printRight(String S1){
        Right[0].setText(S1);
        Right[1].setText("");
        Right[2].setText("");
        Right[3].setText("");
    }

    public void printRight(String S1, String S2){
        Right[0].setText(S1);
        Right[1].setText(S2);
        Right[2].setText("");
        Right[3].setText("");
    }

    public void printRight(String S1, String S2, String S3){
        Right[0].setText(S1);
        Right[1].setText(S2);
        Right[2].setText(S3);
        Right[3].setText("");
    }

    public void printRight(String S1, String S2, String S3, String S4){
        Right[0].setText(S1);
        Right[1].setText(S2);
        Right[2].setText(S3);
        Right[3].setText(S4);
    }


}
