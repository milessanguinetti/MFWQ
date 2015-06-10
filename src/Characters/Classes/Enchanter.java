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
    public void jobDing(playerCharacter toLevel) {
        if(jlevel == 2){
            Skills.Insert(new orderedDLLNode(new enchanterInvokeStone()));
            toLevel.printName();
            System.out.println(" learned Invoke Stone!");
        }
        if(jlevel == 4){ //level 4 active skill
            Skills.Insert(new orderedDLLNode(new enchanterInvokeStorm()));
            toLevel.printName();
            System.out.println(" learned Invoke Storm!");
        }
        if(jlevel == 6){ //level 6 active skill
            Skills.Insert(new orderedDLLNode(new enchanterInvokeWater()));
            toLevel.printName();
            System.out.println(" learned Invoke Water!");
        }
        if(jlevel == 8){ //level 8 active skill
            Skills.Insert(new orderedDLLNode(new enchanterImbueWeapon()));
            toLevel.printName();
            System.out.println(" learned Imbue Weapon!");
        }
        if(jlevel == 10){ //level 10 passive skill NYI
            toLevel.addPassive(new armorOfStone());
            toLevel.printName();
            System.out.println(" learned the passive skill Armor of Stone!");
        }
        if(jlevel == 12){ //level 12 active skill
            Skills.Insert(new orderedDLLNode(new enchanterInvokeSteel()));
            toLevel.printName();
            System.out.println(" learned Invoke Steel!");
        }
        if(jlevel == 14){ //level 14 active skill
            Skills.Insert(new orderedDLLNode(new enchanterElementalStorm()));
            toLevel.printName();
            System.out.println(" learned Elemental Storm!");
        }
        if(jlevel == 16){ //level 16 active skill
            Skills.Insert(new orderedDLLNode(new enchanterEtherealArmor()));
            toLevel.printName();
            System.out.println(" learned Ethereal Armor!");
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
