package Characters.Classes;

import Characters.Skills.Passive.Alertness;
import Characters.Skills.firstClass.*;
import Characters.playerCharacter;
import Structures.LLLnode;

/**
 * Created by Miles Sanguinetti on 4/28/2015.
 */
//rudimentary speed/strength melee class
public class Rogue extends characterClass{
    public Rogue(){
        super("Rogue");
        Skills.Insert(new LLLnode(new rogueEnvenomedBlade())); //initial skill
    }

    @Override
    public void baseDing(playerCharacter toLevel, int level) {
        if(level % 2 == 0)
            toLevel.incrementStat(0, 1); //add a str
        else
            toLevel.incrementStat(2, 1); //add a spd
    }

    @Override
    public void jobDing(playerCharacter toLevel) {
        if(jlevel == 2){ //level 2 active skill
            Skills.Insert(new LLLnode(new rogueSteal()));
            toLevel.printName();
            System.out.println(" learned Steal!");
        }
        if(jlevel == 4){ //level 4 active skill
            Skills.Insert(new LLLnode(new rogueKnifeBarrage()));
            toLevel.printName();
            System.out.println(" learned Knife Barrage!");
        }
        if(jlevel == 6){ //level 6 active skill
            Skills.Insert(new LLLnode(new rogueAmplifyPoison()));
            toLevel.printName();
            System.out.println(" learned !");
        }
        if(jlevel == 8){ //level 8 active skill
            Skills.Insert(new LLLnode(new roguePilferDefenses()));
            toLevel.printName();
            System.out.println(" learned !");
        }
        if(jlevel == 10){ //level 10 passive skill
            toLevel.addPassive(new Alertness());
            toLevel.printName();
            System.out.println(" learned the passive skill Alertness!");
        }
        if(jlevel == 12){ //level 12 active skill
            Skills.Insert(new LLLnode(new rogueQuickenReflexes()));
            toLevel.printName();
            System.out.println(" learned Quicken Reflexes!");
        }
        if(jlevel == 14){ //level 14 active skill
            Skills.Insert(new LLLnode(new rogueRapidStriking()));
            toLevel.printName();
            System.out.println(" learned Rapid Striking!");
        }
        if(jlevel == 16){ //level 16 active skill
            Skills.Insert(new LLLnode(new rogueDisorientingSmokeBomb()));
            toLevel.printName();
            System.out.println(" learned Disorienting Smoke Bomb!");
        }
        if(jlevel == 18){ //level 18 passive skill
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
