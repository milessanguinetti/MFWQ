package Characters.Classes;

import Characters.Skills.Skill;
import Characters.playerCharacter;
import Structures.LLLnode;

/**
 * Created by Miles on 4/28/2015.
 */
//rudimentary speed/strength melee class
public class Rogue extends characterClass{
    public Rogue(){
        super("Rogue");
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
        if(jlevel == 2){
            Skills.Insert(new LLLnode());
            toLevel.printName();
            System.out.println(" learned Bash!");
            Skills.Insert(new LLLnode());
            toLevel.printName();
            System.out.println(" learned Wide Slash!");
            Skills.Insert(new LLLnode());
            toLevel.printName();
            System.out.println(" learned Rend!");
        }
        if(jlevel == 5)
            Skills.Insert(new LLLnode());
    }

    @Override
    public boolean canUseHeavyArmor() {
        return false;
    }
}
