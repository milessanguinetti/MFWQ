package Characters.Classes;

import Characters.Skills.Passive.Alertness;
import Characters.Skills.firstClass.*;
import Characters.playerCharacter;
import Structures.LLLnode;
import Structures.orderedDLLNode;

/**
 * Created by Miles Sanguinetti on 4/28/2015.
 */
//rudimentary speed/strength melee class
public class Rogue extends characterClass{
    public Rogue(){
        super("Rogue");
        Skills.Insert(new orderedDLLNode(new rogueEnvenomedBlade())); //initial skill
    }

    @Override
    public String getClassDescription(){
        return "Sellswords. Highwaymen. Tricksters and charlatans. The rogue is the favored class of the lot of them";
    }

    @Override
    public void baseDing(playerCharacter toLevel, int level) {
        if(level % 2 == 0)
            toLevel.incrementStat(0, 1); //add a str
        else
            toLevel.incrementStat(2, 1); //add a spd
    }

    @Override
    public String jobDing(playerCharacter toLevel) {
        if(jlevel == 2){ //level 2 active skill
            Skills.Insert(new orderedDLLNode(new rogueSteal()));
            return (toLevel.getName() +" learned Steal!");
        }
        if(jlevel == 4){ //level 4 active skill
            Skills.Insert(new orderedDLLNode(new rogueKnifeBarrage()));
            return (toLevel.getName() +" learned Knife Barrage!");
        }
        if(jlevel == 6){ //level 6 active skill
            Skills.Insert(new orderedDLLNode(new rogueAmplifyPoison()));
            return (toLevel.getName() +" learned Amplify Poison!");
        }
        if(jlevel == 8){ //level 8 active skill
            Skills.Insert(new orderedDLLNode(new roguePilferDefenses()));
            return (toLevel.getName() +" learned Pilfer Defenses!");
        }
        if(jlevel == 10){ //level 10 passive skill
            toLevel.addPassive(new Alertness());
            return (toLevel.getName() +" learned the passive skill Alertness!");
        }
        if(jlevel == 12){ //level 12 active skill
            Skills.Insert(new orderedDLLNode(new rogueQuickenReflexes()));
            return (toLevel.getName() +" learned Quicken Reflexes!");
        }
        if(jlevel == 14){ //level 14 active skill
            Skills.Insert(new orderedDLLNode(new rogueRapidStriking()));
            return (toLevel.getName() +" learned Rapid Striking!");
        }
        if(jlevel == 16){ //level 16 active skill
            Skills.Insert(new orderedDLLNode(new rogueDisorientingSmokeBomb()));
            return (toLevel.getName() +" learned Disorienting Smoke Bomb!");
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
