package Characters.Classes;

import Characters.Skills.*;
import Characters.playerCharacter;
import Structures.LLLnode;


/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
//rudimentary melee class focusing on str and vit.
public class Soldier extends characterClass {
    public Soldier(){
        super("Soldier");
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
            Skills.Insert(new LLLnode(new soldierBash()));
            toLevel.printName();
            System.out.println(" learned Bash!");
            Skills.Insert(new LLLnode(new soldierWideSlash()));
            toLevel.printName();
            System.out.println(" learned Wide Slash!");
            Skills.Insert(new LLLnode(new soldierRend()));
            toLevel.printName();
            System.out.println(" learned Rend!");
        }
        if(jlevel == 5)
            Skills.Insert(new LLLnode(new soldierUnbreakableShield()));
    }

    @Override
    public Skill getSkill(int toGet) {
        return super.getSkill(toGet);
    }

    @Override
    public void displaySkills() {
        super.displaySkills();
    }

    @Override
    public boolean canUseHeavyArmor() {
        return false;
    }
}