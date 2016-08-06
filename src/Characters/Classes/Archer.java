package Characters.Classes;

import Characters.Skills.firstClass.*;
import Characters.playerCharacter;
import Structures.LLLnode;
import Structures.orderedDLLNode;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class Archer extends characterClass{
    public Archer(){
        super("Archer");
        Skills.Insert(new orderedDLLNode(new archerLaceratingArrow()));
    }

    @Override
    public String getClassDescription(){
        return "A swift, bow-wielding class that attacks foes from afar";
    }

    @Override
    public void baseDing(playerCharacter toLevel, int level) {
        if(level % 2 == 0)
            toLevel.incrementStat(2, 1); //add a speed
        else
            toLevel.incrementStat(1, 1); //add a dex
    }

    @Override
    public String jobDing(playerCharacter toLevel) {
        if(jlevel == 2){ //level 2 active skill
            Skills.Insert(new orderedDLLNode(new archerArmorPiercingArrow()));
            return (toLevel.getName() +" learned Armor Piercing Arrow!");
        }
        if(jlevel == 4){ //level 4 active skill
            Skills.Insert(new orderedDLLNode(new archerArrowStorm()));
            return (toLevel.getName() +" learned Arrow Storm!");
        }
        if(jlevel == 6){ //level 6 active skill
            Skills.Insert(new orderedDLLNode(new archerArcingArrow()));
            return (toLevel.getName() +" learned Arcing Arrow!");
        }
        if(jlevel == 8){ //level 8 active skill
            Skills.Insert(new orderedDLLNode(new archerExposingArrow()));
            return (toLevel.getName() +" learned Exposing Arrow!");
        }
        if(jlevel == 10){ //level 10 passive skill NYI
            toLevel.addPassive(null);
            return (toLevel.getName() +" learned !");
        }
        if(jlevel == 12){ //level 12 active skill
            Skills.Insert(new orderedDLLNode(new archerCripplingShot()));
            return (toLevel.getName() +" learned Crippling Shot!");
        }
        if(jlevel == 14){ //level 14 active skill
            Skills.Insert(new orderedDLLNode(new archerOverdraw()));
            return (toLevel.getName() +" learned Overdraw!");
        }
        if(jlevel == 16){ //level 16 active skill
            Skills.Insert(new orderedDLLNode(new archerFinishingShot()));
            return (toLevel.getName() +" learned Finishing Shot!");
        }
        if(jlevel == 18) { //level 18 passive skill
            toLevel.addPassive(null);
            return (toLevel.getName() +" learned !");
        }
        if(jlevel == 20){ //level 20 stat boost
            toLevel.incrementAll(); //increment every stat
        }
        return "";
    }

    @Override
    public boolean canUseHeavyArmor() {
        return false;
    }
}
