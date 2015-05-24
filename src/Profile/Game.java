package Profile;

import Characters.Classes.*;
import Characters.Inventory.Weapons.generic1hEdged;
import Characters.Inventory.Weapons.genericGun;
import Characters.Monsters.Kobold;
import Characters.Monsters.babyKobold;
import Characters.gameCharacter;
import Characters.playerCharacter;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.Random;

/**
 * Created by Miles Sanguinetti on 3/29/15.
 */
public class Game extends JFrame{
    //the player's user profile; public and static because only one will be used at a time;
    //it would be nonsensical to pass it around into virtually every function I call
    public static userProfile Player; //the game's player.
    private Frame currentFrame; //the current frame that we're in.

    public void Exit(){
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public Game(){
        Player = new userProfile();
        initializeInterface();
    }

    private void initializeInterface(){
        //currentFrame = new Menu(this);
        currentFrame = new Battle();
        add(currentFrame); //add a menu object (a type of jpanel) to the jframe once it is opened.
        setSize(1000, 800); //size is 1000 by 800 pixels
        setTitle("Motherfucking Wizard Quest"); //title the application appropriately
        setDefaultCloseOperation(EXIT_ON_CLOSE); //close the application once it is commanded to close
        setLocationRelativeTo(null); //centers the window
    }

    public void swapFrame(Frame toSwap){
        removeAll();
        getContentPane().remove(currentFrame); //remove the current frame.
        revalidate();
        repaint();
        currentFrame = toSwap;
        add(currentFrame);
        revalidate();
        repaint();
    }

    public void Test(){
        playerCharacter bob = new playerCharacter("Sergeant Pepper", "Faithful",
                350, 100, 10, 10, 10, 10, 10, 10, 0);
        Player.addCharacter(bob);
        bob.setLeft(new genericGun(6));
        bob.setRight(new generic1hEdged(6)); //equip the good sergeant with a randomly generated weapon
        bob.addClass(new Soldier());
        bob.addClass(new Rogue());
        bob.addClass(new Alchemist());
        bob.addClass(new Inquisitor());
        bob.setPrimaryClass(new Primalist());

        Random Rand = new Random();
        gameCharacter[] Foes = new gameCharacter[4];
        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 4; ++j)
                Foes[j] = null; //clean out foes
            Battle WEFIGHTNOW = new Battle();
            swapFrame(WEFIGHTNOW);
            Game.Player.setCurrentBattle(WEFIGHTNOW); //initialize a new battle
            if(Rand.nextInt(2) == 0){
                Foes[0] = new Kobold();
                Foes[1] = new babyKobold();
                Foes[2] = new Kobold();
            }
            else{
                Foes[0] = new babyKobold();
                Foes[1] = new Kobold();
                Foes[2] = new babyKobold();
            }
            if(!Game.Player.getCurrentBattle().commenceBattle(Game.Player.getParty(), Foes))
                break;
        }
    }

}
