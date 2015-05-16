package Characters.Classes;

import Characters.Skills.secondClass.*;
import Characters.playerCharacter;
import Structures.LLLnode;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
public class Crusader extends characterClass{
    public Crusader(){
        super("Crusader", 10000);
        Skills.Insert(new LLLnode(new crusaderHolyBlade())); //initial skill
    }

    @Override
    public void baseDing(playerCharacter toLevel, int level) {
        if(level % 2 == 0) {
            toLevel.incrementStat(0, 2); //add two str
            toLevel.incrementStat(4, -1); //lose one int
        }
        else
            toLevel.incrementStat(5, 2); //add two faith
    }

    @Override
    public void jobDing(playerCharacter toLevel) {
        if (jlevel == 2) { //level 2 active skill
            Skills.Insert(new LLLnode(new crusaderDeusVult()));
            toLevel.printName();
            System.out.println(" learned Deus Vult!");
        }
        if (jlevel == 4) { //level 4 active skill
            Skills.Insert(new LLLnode(new crusaderCallArtillery()));
            toLevel.printName();
            System.out.println(" learned Call Artillery!");
        }
        if (jlevel == 6) { //level 6 active skill
            Skills.Insert(new LLLnode(new crusaderLayingOnofHands()));
            toLevel.printName();
            System.out.println(" learned Laying On of Hands!");
        }
        if (jlevel == 8) { //level 8 active skill
            Skills.Insert(new LLLnode(new crusaderMartyrsBurningBlade()));
            toLevel.printName();
            System.out.println(" learned Martyr's Burning Blade!");
        }
        /*if (jlevel == 10) { //level 10 passive skill
            toLevel.addPassive(new ());
            toLevel.printName();
            System.out.println(" learned the passive skill !");
        }*/
    }

    @Override
    public boolean canUseHeavyArmor() {
        return true;
    }
}
