package Characters.Classes;

import Characters.playerCharacter;
import Structures.LLLnode;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class Archer extends characterClass{
    public Archer(){
        super("Archer");
        Skills.Insert(new LLLnode(new soldierBash()));
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
        if(jlevel == 2){
            Skills.Insert(new LLLnode(new soldierRend()));
            toLevel.printName();
            System.out.println(" learned Rend!");
        }
        if(jlevel == 4){ //level 4 active skill
            Skills.Insert(new LLLnode(new soldierWideSlash()));
            toLevel.printName();
            System.out.println(" learned Wide Slash!");
        }
        if(jlevel == 6){ //level 6 active skill
            Skills.Insert(new LLLnode(new soldierWideSlash())); //NEED NEW SKILL HERE
            toLevel.printName();
            System.out.println(" learned Wide Slash!");
        }
        if(jlevel == 8){ //level 8 active skill
            Skills.Insert(new LLLnode(new defenderUnbreakableShield()));
            toLevel.printName();
            System.out.println(" learned Unbreakable Shield!");
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
