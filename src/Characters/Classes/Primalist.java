package Characters.Classes;

import Characters.Skills.firstClass.*;
import Characters.playerCharacter;
import Structures.LLLnode;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class Primalist extends characterClass{
    public Primalist(){
        super("Primalist");
        Skills.Insert(new LLLnode(new primalistBogMiasma()));
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
            Skills.Insert(new LLLnode(new primalistCallStorm()));
            toLevel.printName();
            System.out.println(" learned Call Storm!");
        }
        if(jlevel == 4){ //level 4 active skill
            Skills.Insert(new LLLnode(new primalistEarthenWard()));
            toLevel.printName();
            System.out.println(" learned Earthern Ward!");
        }
        if(jlevel == 6){ //level 6 active skill
            Skills.Insert(new LLLnode(new primalistAnimalisticStrike()));
            toLevel.printName();
            System.out.println(" learned Animalistic Strike!");
        }
        if(jlevel == 8){ //level 8 active skill
            Skills.Insert(new LLLnode(new primalistRejuvenatingBreeze()));
            toLevel.printName();
            System.out.println(" learned Rejuvenating Breeze!");
        }
        if(jlevel == 10){ //level 10 passive skill NYI
            toLevel.addPassive(null);
            toLevel.printName();
            System.out.println(" learned !");
        }
        if(jlevel == 12){ //level 12 active skill
            Skills.Insert(new LLLnode(new rogueSteal()));
            toLevel.printName();
            System.out.println(" learned !");
        }
        if(jlevel == 14){ //level 14 active skill
            Skills.Insert(new LLLnode(new rogueSteal()));
            toLevel.printName();
            System.out.println(" learned !");
        }
        if(jlevel == 16){ //level 16 active skill
            Skills.Insert(new LLLnode(new rogueSteal()));
            toLevel.printName();
            System.out.println(" learned !");
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
