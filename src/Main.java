import Characters.Classes.Soldier;
import Characters.Inventory.Weapons.koboldSlayingSword;
import Characters.Monsters.Kobold;
import Characters.Monsters.babyKobold;
import Characters.gameCharacter;
import Characters.playerCharacter;
import Profile.Battle;
import Profile.Game;
import sun.org.mozilla.javascript.tools.shell.Global;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Game newGame = new Game(); //initialize a new game
        playerCharacter bob = new playerCharacter("Sergeant Pepper", "Human (Faithful)",
                350, 100, 10, 10, 10, 10, 10, 10, 0);
        newGame.Player.addCharacter(bob);
        bob.setRight(new koboldSlayingSword()); //equip the good sergeant with a shiny new kobold slaying sword
        bob.addClass(new Soldier());
        bob.chooseClass();

        Random Rand = new Random();
        gameCharacter [] Foes = new gameCharacter[4];
        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 4; ++j)
                Foes[j] = null; //clean out foes
            Game.Player.setCurrentBattle(new Battle()); //initialize a new battle
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
