package Characters.Classes;

import Characters.Skills.firstClass.*;
import Characters.playerCharacter;
import Structures.LLLnode;
import Structures.orderedDLLNode;

/**
 * Created by Miles Sanguinetti on 5/1/2015.
 */
public class Alchemist extends characterClass{
    public Alchemist(){
        super("Alchemist");
        Skills.Insert(new orderedDLLNode(new alchemistTransmuteBlood())); //initial skill
    }

    @Override
    public String getClassDescription(){
        return "A master of potions and transmutation that supports allies with the power of science";
    }

    @Override
    public void baseDing(playerCharacter toLevel, int level) {
        if(level % 2 == 0)
            toLevel.incrementStat(1, 1); //add a dex
        else
            toLevel.incrementStat(4, 1); //add an int
    }

    @Override
    public String jobDing(playerCharacter toLevel) {
        if(jlevel == 2){ //level 2 active skill
            Skills.Insert(new orderedDLLNode(new alchemistIncendiaryBullet()));
            return (toLevel.getName() +" learned Incendiary Bullet!");
        }
        if(jlevel == 4){ //level 4 active skill
            Skills.Insert(new orderedDLLNode(new alchemistPotionMixing()));
            return (toLevel.getName() +" learned Potion Mixing!");
        }
        if(jlevel == 6){ //level 6 active skill
            Skills.Insert(new orderedDLLNode(new alchemistTransmuteGoldenBullet()));
            return (toLevel.getName() +" learned Transmute: Golden Bullet!");
        }
        if(jlevel == 8){ //level 8 active skill
            Skills.Insert(new orderedDLLNode(new alchemistAlchemicalTreatment()));
            return (toLevel.getName() +" learned Alchemical Treatment!");
        }
        if(jlevel == 10){ //level 10 passive skill
            toLevel.addPassive(null);
            return (toLevel.getName() +" learned !");
        }
        if(jlevel == 12){ //level 12 active skill
            Skills.Insert(new orderedDLLNode(new alchemistCorrosiveVial()));
            return (toLevel.getName() +" learned Corrosive Vial!");
        }
        if(jlevel == 14){ //level 14 active skill
            Skills.Insert(new orderedDLLNode(new alchemistUnpredictablePotion()));
            return (toLevel.getName() +" learned Unpredictable Potion!");
        }
        if(jlevel == 16){ //level 16 active skill
            Skills.Insert(new orderedDLLNode(new alchemistTransmutePStone()));
            return (toLevel.getName() +" learned Transmute: Philosopher's Stone!");
        }
        if(jlevel == 18){ //level 18 passive skill
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
