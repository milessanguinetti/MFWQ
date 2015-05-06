package Characters.Classes;

import Characters.Skills.firstClass.*;
import Characters.playerCharacter;
import Structures.LLLnode;

/**
 * Created by Miles Sanguinetti on 5/1/2015.
 */
public class Alchemist extends characterClass{
    public Alchemist(){
        super("Alchemist");
        Skills.Insert(new LLLnode(new alchemistTransmuteBlood())); //initial skill
    }

    @Override
    public void baseDing(playerCharacter toLevel, int level) {
        if(level % 2 == 0)
            toLevel.incrementStat(1, 1); //add a dex
        else
            toLevel.incrementStat(4, 1); //add an int
    }

    @Override
    public void jobDing(playerCharacter toLevel) {
        if(jlevel == 2){ //level 2 active skill
            Skills.Insert(new LLLnode(new alchemistTransmuteGoldenBullet()));
            toLevel.printName();
            System.out.println(" learned Transmute: Golden Bullet!");
        }
        if(jlevel == 4){ //level 4 active skill
            Skills.Insert(new LLLnode(new alchemistPotionMixing()));
            toLevel.printName();
            System.out.println(" learned Potion Mixing!");
        }
        if(jlevel == 6){ //level 6 active skill
            Skills.Insert(new LLLnode(new alchemistIncendiaryBullet()));
            toLevel.printName();
            System.out.println(" learned Incendiary Bullet!");
        }
        if(jlevel == 8){ //level 8 active skill
            Skills.Insert(new LLLnode(new alchemistAlchemicalTreatment()));
            toLevel.printName();
            System.out.println(" learned Alchemical Treatment!");
        }
        if(jlevel == 10){ //level 10 passive skill
            toLevel.addPassive(null);
            toLevel.printName();
            System.out.println(" learned !");
        }
        if(jlevel == 12){ //level 12 active skill
            Skills.Insert(new LLLnode(new alchemistCorrosiveVial()));
            toLevel.printName();
            System.out.println(" learned Corrosive Vial!");
        }
        if(jlevel == 14){ //level 14 active skill
            Skills.Insert(new LLLnode(new rogueSteal()));
            toLevel.printName();
            System.out.println(" learned !");
        }
        if(jlevel == 16){ //level 16 active skill
            Skills.Insert(new LLLnode(new alchemistTransmutePStone()));
            toLevel.printName();
            System.out.println(" learned Transmute: Philosopher's Stone!");
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
