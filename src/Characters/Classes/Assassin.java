package Characters.Classes;

import Characters.Skills.Passive.ambushMastery;
import Characters.Skills.secondClass.*;
import Characters.playerCharacter;
import Structures.orderedDLLNode;

/**
 * Created by Spaghetti on 8/16/2016.
 */
public class Assassin extends characterClass{
    public Assassin(){
        super("Assassin", 10000);
        Skills.Insert(new orderedDLLNode(new assassinLightningStrikes())); //initial skill
    }

    @Override
    public String getClassDescription(){
        return "A silent killer that dispatches foes with unsurpassed speed.";
    }

    @Override
    public void baseDing(playerCharacter toLevel, int level) {
        if(level % 3 == 0) {
            toLevel.incrementStat(0, 2); //add two str
            toLevel.incrementStat(5, -1); //lose one faith
        }
        else if(level % 3 == 1){
            toLevel.incrementStat(2, 2); //add two spd
            toLevel.incrementStat(0, 1); //add one dex
            toLevel.incrementStat(3, -1); //lose one vit
        }
        else
            toLevel.incrementStat(2, 2); //add two spd
    }

    @Override
    public String jobDing(playerCharacter toLevel) {
        if (jlevel == 2) { //level 2 active skill
            Skills.Insert(new orderedDLLNode(new assassinVenomCarve()));
            return (toLevel.getName() +" learned Venom Carve!");
        }
        if (jlevel == 4) { //level 4 active skill
            Skills.Insert(new orderedDLLNode(new assassinCaltrops()));
            return (toLevel.getName() +" learned Caltrops!");
        }
        if (jlevel == 6) { //level 6 active skill
            Skills.Insert(new orderedDLLNode(new assassinPoise()));
            return (toLevel.getName() +" learned Poise!");
        }
        if (jlevel == 8) { //level 8 active skill
            Skills.Insert(new orderedDLLNode(new assassinKillingIntent()));
            return (toLevel.getName() +" learned Killing Intent!");
        }
        if (jlevel == 10) { //level 10 passive skill
            toLevel.addPassive(new ambushMastery());
            return (toLevel.getName() +" learned the passive skill Ambush Mastery!");
        }
        return "";
    }

    @Override
    public boolean canUseHeavyArmor() {
        return false;
    }
}
