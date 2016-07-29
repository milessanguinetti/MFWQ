package Characters.Classes;

import Characters.Skills.firstClass.*;
import Characters.playerCharacter;
import Structures.LLLnode;
import Structures.orderedDLLNode;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class Primalist extends characterClass{
    public Primalist(){
        super("Primalist");
        Skills.Insert(new orderedDLLNode(new primalistBogMiasma()));
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
            Skills.Insert(new orderedDLLNode(new primalistCallStorm()));
            return (toLevel.getName() +" learned Call Storm!");
        }
        if(jlevel == 4){ //level 4 active skill
            Skills.Insert(new orderedDLLNode(new primalistEarthenWard()));
            return (toLevel.getName() +" learned Earthern Ward!");
        }
        if(jlevel == 6){ //level 6 active skill
            Skills.Insert(new orderedDLLNode(new primalistAnimalisticStrike()));
            return (toLevel.getName() +" learned Animalistic Strike!");
        }
        if(jlevel == 8){ //level 8 active skill
            Skills.Insert(new orderedDLLNode(new primalistRejuvenatingBreeze()));
            return (toLevel.getName() +" learned Rejuvenating Breeze!");
        }
        if(jlevel == 10){ //level 10 passive skill NYI
            toLevel.addPassive(null);
            return (toLevel.getName() +" learned !");
        }
        if(jlevel == 12){ //level 12 active skill
            Skills.Insert(new orderedDLLNode(new primalistSeedOfHealing()));
            return (toLevel.getName() +" learned Seed Of Healing!");
        }
        if(jlevel == 14){ //level 14 active skill
            Skills.Insert(new orderedDLLNode(new primalistDevouringGrowth()));
            return (toLevel.getName() +" learned Devouring Growth!");
        }
        if(jlevel == 16){ //level 16 active skill
            Skills.Insert(new orderedDLLNode(new primalistDevouringBloom()));
            return (toLevel.getName() +" learned Devouring Bloom!");
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
