package Characters.Classes;

import Characters.playerCharacter;
import Structures.LLLnode;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
public class Titan extends characterClass{
    public Titan(){
        super("Titan", 10000);
        Skills.Insert(new LLLnode(new ())); //initial skill
    }

    @Override
    public void baseDing(playerCharacter toLevel, int level) {
        if(level % 2 == 0) {
            toLevel.incrementStat(0, 2); //add two str
            toLevel.incrementStat(2, -1); //lose one spd
        }
        else
            toLevel.incrementStat(3, 2); //add two vit
    }

    @Override
    public void jobDing(playerCharacter toLevel) {
        if (jlevel == 2) { //level 2 active skill
            Skills.Insert(new LLLnode(new ()));
            toLevel.printName();
            System.out.println(" learned !");
        }
        if (jlevel == 4) { //level 4 active skill
            Skills.Insert(new LLLnode(new ()));
            toLevel.printName();
            System.out.println(" learned !");
        }
        if (jlevel == 6) { //level 6 active skill
            Skills.Insert(new LLLnode(new ()));
            toLevel.printName();
            System.out.println(" learned !");
        }
        if (jlevel == 8) { //level 8 active skill
            Skills.Insert(new LLLnode(new ()));
            toLevel.printName();
            System.out.println(" learned !");
        }
        if (jlevel == 10) { //level 10 passive skill
            toLevel.addPassive(new ());
            toLevel.printName();
            System.out.println(" learned the passive skill !");
        }
    }

    @Override
    public boolean canUseHeavyArmor() {
        return true;
    }
}
