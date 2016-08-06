package Characters.Classes;

import Characters.Skills.secondClass.*;
import Characters.playerCharacter;
import Structures.LLLnode;
import Structures.orderedDLLNode;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
public class Crusader extends characterClass{
    public Crusader(){
        super("Crusader", 10000);
        Skills.Insert(new orderedDLLNode(new crusaderHolyBlade())); //initial skill
    }

    @Override
    public String getClassDescription(){
        return "A fearless paragon of the light that tirelessly hunts unholy powers, wherever they appear";
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
    public String jobDing(playerCharacter toLevel) {
        if (jlevel == 2) { //level 2 active skill
            Skills.Insert(new orderedDLLNode(new crusaderDeusVult()));
            return (toLevel.getName() +" learned Deus Vult!");
        }
        if (jlevel == 4) { //level 4 active skill
            Skills.Insert(new orderedDLLNode(new crusaderCallArtillery()));
            return (toLevel.getName() +" learned Call Artillery!");
        }
        if (jlevel == 6) { //level 6 active skill
            Skills.Insert(new orderedDLLNode(new crusaderLayingOnofHands()));
            return (toLevel.getName() +" learned Laying On of Hands!");
        }
        if (jlevel == 8) { //level 8 active skill
            Skills.Insert(new orderedDLLNode(new crusaderMartyrsBurningBlade()));
            return (toLevel.getName() +" learned Martyr's Burning Blade!");
        }
        /*if (jlevel == 10) { //level 10 passive skill
            toLevel.addPassive(new ());
            return (toLevel.getName() +" learned the passive skill !");
        }*/
        return "";
    }

    @Override
    public boolean canUseHeavyArmor() {
        return true;
    }
}
