package Characters.Classes;

import Characters.Skills.secondClass.titanAmplifyMetabolism;
import Characters.Skills.secondClass.titanGigasModus;
import Characters.Skills.secondClass.titanRampage;
import Characters.playerCharacter;
import Structures.LLLnode;
import Structures.orderedDLLNode;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
public class Titan extends characterClass{
    public Titan(){
        super("Titan", 10000);
        Skills.Insert(new orderedDLLNode(new titanGigasModus())); //initial skill
    }

    @Override
    public String getClassDescription(){
        return "An emblem of scientific might, the titan class gives homunculi incredible fortitude and powers of regeneration";
    }

    @Override
    public void baseDing(playerCharacter toLevel, int level) {
        if(level % 2 == 0) {
            toLevel.incrementStat(0, 2); //add two str
            toLevel.incrementStat(2, -1); //lose one spd
        }
        else
            toLevel.incrementStat(3, 2); //add two vit
    }

    @Override
    public String jobDing(playerCharacter toLevel) {
        if (jlevel == 2) { //level 2 active skill
            Skills.Insert(new orderedDLLNode(new titanRampage()));
            return (toLevel.getName() + " learned Rampage!");
        }
        if (jlevel == 4) { //level 4 active skill
            Skills.Insert(new orderedDLLNode(new titanAmplifyMetabolism()));
            return (toLevel.getName() + " learned Amplify Metabolism!");
        }
        /*if (jlevel == 6) { //level 6 active skill
            Skills.Insert(new orderedDLLNode(new ()));
            return (toLevel.getName() + " learned !");
        }
        if (jlevel == 8) { //level 8 active skill
            Skills.Insert(new orderedDLLNode(new ()));
            return (toLevel.getName() + " learned !");
        }
        if (jlevel == 10) { //level 10 passive skill
            toLevel.addPassive(new ());
            return (toLevel.getName() + " learned the passive skill !");
        }*/
        return "";
    }

    @Override
    public boolean canUseHeavyArmor() {
        return true;
    }
}
