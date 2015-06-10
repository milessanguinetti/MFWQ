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
    public void baseDing(playerCharacter toLevel, int level) {
        if(level % 2 == 0)
            toLevel.incrementStat(2, 1); //add a speed
        else
            toLevel.incrementStat(1, 1); //add a dex
    }

    @Override
    public void jobDing(playerCharacter toLevel) {
        if(jlevel == 2){ //level 2 active skill
            Skills.Insert(new orderedDLLNode(new archerArmorPiercingArrow()));
            toLevel.printName();
            System.out.println(" learned Armor Piercing Arrow!");
        }
        if(jlevel == 4){ //level 4 active skill
            Skills.Insert(new orderedDLLNode(new archerArrowStorm()));
            toLevel.printName();
            System.out.println(" learned Arrow Storm!");
        }
        if(jlevel == 6){ //level 6 active skill
            Skills.Insert(new orderedDLLNode(new archerArcingArrow()));
            toLevel.printName();
            System.out.println(" learned Arcing Arrow!");
        }
        if(jlevel == 8){ //level 8 active skill
            Skills.Insert(new orderedDLLNode(new archerExposingArrow()));
            toLevel.printName();
            System.out.println(" learned Exposing Arrow!");
        }
        if(jlevel == 10){ //level 10 passive skill NYI
            toLevel.addPassive(null);
            toLevel.printName();
            System.out.println(" learned !");
        }
        if(jlevel == 12){ //level 12 active skill
            Skills.Insert(new orderedDLLNode(new archerCripplingShot()));
            toLevel.printName();
            System.out.println(" learned Crippling Shot!");
        }
        if(jlevel == 14){ //level 14 active skill
            Skills.Insert(new orderedDLLNode(new archerOverdraw()));
            toLevel.printName();
            System.out.println(" learned Overdraw!");
        }
        if(jlevel == 16){ //level 16 active skill
            Skills.Insert(new orderedDLLNode(new archerFinishingShot()));
            toLevel.printName();
            System.out.println(" learned Finishing Shot!");
        }
        if(jlevel == 18) { //level 18 passive skill
            toLevel.addPassive(null);
            toLevel.printName();
            System.out.println(" learned !");
        }
        if(jlevel == 20){ //level 20 stat boost
            toLevel.incrementAll(); //increment every stat
        }
    }

    @Override
    public boolean canUseHeavyArmor() {
        return false;
    }
}
