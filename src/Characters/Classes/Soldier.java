package Characters.Classes;

import Characters.Skills.firstClass.*;
import Characters.playerCharacter;
import Structures.LLLnode;


/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
//rudimentary melee class focusing on str and vit.
public class Soldier extends characterClass {
    public Soldier(){
        super("Soldier");
        Skills.Insert(new LLLnode(new soldierBash()));
    }

    @Override
    public void baseDing(playerCharacter toLevel, int level) {
        if(level % 2 == 0)
            toLevel.incrementStat(0, 1); //add a str
        else
            toLevel.incrementStat(3, 1); //add a vit
    }

    @Override
    public void jobDing(playerCharacter toLevel) {
        if(jlevel == 2){
            Skills.Insert(new LLLnode(new soldierGloriousExecution()));
            toLevel.printName();
            System.out.println(" learned Glorious Execution!");
        }
        if(jlevel == 4){ //level 4 active skill
            Skills.Insert(new LLLnode(new soldierWideSlash()));
            toLevel.printName();
            System.out.println(" learned Wide Slash!");
        }
        if(jlevel == 6){ //level 6 active skill
            Skills.Insert(new LLLnode(new soldierRend()));
            toLevel.printName();
            System.out.println(" learned Rend!");
        }
        if(jlevel == 8){ //level 8 active skill
            Skills.Insert(new LLLnode(new soldierDefend()));
            toLevel.printName();
            System.out.println(" learned Defend!");
        }
        if(jlevel == 10){ //level 10 passive skill/second classes
            toLevel.addPassive(null);
            toLevel.printName();
            System.out.println(" learned !");
            if(toLevel.isOfRace("Faithful")){
                toLevel.addClass(new Crusader()); //add crusader class
                toLevel.addClass(new Defender()); //add defender class
                System.out.println("The Crusader and Defender classes are now available!");
            }
            else if(toLevel.isOfRace("Heretic")){
                toLevel.addClass(new Berserker()); //add berserker class
                toLevel.addClass(new Idolator()); //add idolator class
                System.out.println("The Berserker and Idolator classes are now available!");
            }
            else if(toLevel.isOfRace("Alraune")){
                toLevel.addClass(new Berserker()); //add berserker class
                toLevel.addClass(new Defender()); //add defender class
                System.out.println("The Berserker and Defender classes are now available!");
            }
            else{//homunculus case
                toLevel.addClass(new Titan()); //add titan class
                System.out.println("The Titan class is now available!");
            }
        }
        if(jlevel == 12){ //level 12 active skill
            Skills.Insert(new LLLnode(new soldierMaintainWeapon()));
            toLevel.printName();
            System.out.println(" learned Maintain Weapon!");
        }
        if(jlevel == 14){ //level 14 active skill
            Skills.Insert(new LLLnode(new soldierMaintainArmor()));
            toLevel.printName();
            System.out.println(" learned Maintain Armor!");
        }
        if(jlevel == 16){ //level 16 active skill
            Skills.Insert(new LLLnode(new soldierSeverHamstring()));
            toLevel.printName();
            System.out.println(" learned Sever Hamstring!");
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
        return true;
    }
}