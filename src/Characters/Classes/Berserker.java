package Characters.Classes;

import Characters.Skills.secondClass.berserkerGoBerserk;
import Characters.Skills.secondClass.berserkerIronHurricane;
import Characters.Skills.secondClass.berserkerRagingSmash;
import Characters.playerCharacter;
import Structures.LLLnode;
import Structures.orderedDLLNode;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
public class Berserker extends characterClass{
    public Berserker(){
        super("Berserker", 10000);
        Skills.Insert(new orderedDLLNode(new berserkerRagingSmash())); //initial skill
    }

    @Override
    public String getClassDescription(){
        return "A bloodthirsty warrior that gleefully trades defense for overwhelming offensive prowess.";
    }

    @Override
    public void baseDing(playerCharacter toLevel, int level) {
        if(level % 2 == 0) {
            toLevel.incrementStat(0, 2); //add two str
            toLevel.incrementStat(4, -1); //lose one int
        }
        else
            toLevel.incrementStat(2, 2); //add two spd
    }

    @Override
    public String jobDing(playerCharacter toLevel) {
        if (jlevel == 2) { //level 2 active skill
            Skills.Insert(new orderedDLLNode(new berserkerGoBerserk()));
            return (toLevel.getName() +" learned Go Berserk!");
        }
        if (jlevel == 4) { //level 4 active skill
            Skills.Insert(new orderedDLLNode(new berserkerIronHurricane()));
            return (toLevel.getName() +" learned Iron Hurricane!");
        }
        /*if (jlevel == 6) { //level 6 active skill
            Skills.Insert(new orderedDLLNode(new ()));
            return (toLevel.getName() +" learned !");
        }
        if (jlevel == 8) { //level 8 active skill
            Skills.Insert(new orderedDLLNode(new ()));
            return (toLevel.getName() +" learned !");
        }
        if (jlevel == 10) { //level 10 passive skill
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
