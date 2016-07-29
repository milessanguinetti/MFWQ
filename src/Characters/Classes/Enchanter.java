package Characters.Classes;

import Characters.Skills.Passive.armorOfStone;
import Characters.Skills.firstClass.*;
import Characters.playerCharacter;
import Structures.LLLnode;
import Structures.orderedDLLNode;

/**
 * Created by Miles Sanguinetti on 5/4/15.
 */
//int/spd caster class.
public class Enchanter extends characterClass{
    public Enchanter(){
        super("Enchanter");
        Skills.Insert(new orderedDLLNode(new enchanterInvokeFlame()));
    }

    @Override
    public void baseDing(playerCharacter toLevel, int level) {
        if(level % 2 == 0)
            toLevel.incrementStat(2, 1); //add a speed
        else
            toLevel.incrementStat(4, 1); //add an int
    }

    @Override
    public String jobDing(playerCharacter toLevel) {
        if(jlevel == 2){
            Skills.Insert(new orderedDLLNode(new enchanterInvokeStone()));
            return (toLevel.getName() +" learned Invoke Stone!");
        }
        if(jlevel == 4){ //level 4 active skill
            Skills.Insert(new orderedDLLNode(new enchanterInvokeStorm()));
            return (toLevel.getName() +" learned Invoke Storm!");
        }
        if(jlevel == 6){ //level 6 active skill
            Skills.Insert(new orderedDLLNode(new enchanterInvokeWater()));
            return (toLevel.getName() +" learned Invoke Water!");
        }
        if(jlevel == 8){ //level 8 active skill
            Skills.Insert(new orderedDLLNode(new enchanterImbueWeapon()));
            return (toLevel.getName() +" learned Imbue Weapon!");
        }
        if(jlevel == 10){ //level 10 passive skill NYI
            toLevel.addPassive(new armorOfStone());
            return (toLevel.getName() +" learned the passive skill Armor of Stone!");
        }
        if(jlevel == 12){ //level 12 active skill
            Skills.Insert(new orderedDLLNode(new enchanterInvokeSteel()));
            return (toLevel.getName() +" learned Invoke Steel!");
        }
        if(jlevel == 14){ //level 14 active skill
            Skills.Insert(new orderedDLLNode(new enchanterElementalStorm()));
            return (toLevel.getName() +" learned Elemental Storm!");
        }
        if(jlevel == 16){ //level 16 active skill
            Skills.Insert(new orderedDLLNode(new enchanterEtherealArmor()));
            return (toLevel.getName() +" learned Ethereal Armor!");
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
