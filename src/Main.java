import Profile.Game;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game newGame = new Game(); //initialize a new game
                newGame.setVisible(true); //make the game visible.
            }
        });
    }
}
