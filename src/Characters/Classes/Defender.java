package Characters.Classes;

import Characters.Skills.secondClass.defenderAnticipate;
import Characters.Skills.secondClass.defenderBulwark;
import Characters.Skills.secondClass.defenderShieldSmash;
import Characters.Skills.secondClass.defenderUnbreakableShield;
import Characters.playerCharacter;
import Structures.LLLnode;
import Structures.orderedDLLNode;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
public class Defender extends characterClass{
    public Defender(){
        super("Defender", 10000);
        Skills.Insert(new orderedDLLNode(new defenderBulwark())); //initial skill
    }

    @Override
    public String getClassDescription(){
        return "A stalwart shield-bearer that protects allies with their knightly prowess";
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
            Skills.Insert(new orderedDLLNode(new defenderShieldSmash()));
            return (toLevel.getName() +" learned !");
        }
        if (jlevel == 4) { //level 4 active skill
            Skills.Insert(new orderedDLLNode(new defenderUnbreakableShield()));
            toLevel.printName();
            System.out.println(" learned !");
        }
        if (jlevel == 6) { //level 6 active skill
            Skills.Insert(new orderedDLLNode(new defenderAnticipate()));
            toLevel.printName();
            System.out.println(" learned !");
        }
        /*if (jlevel == 8) { //level 8 active skill
            Skills.Insert(new orderedDLLNode(new ()));
            toLevel.printName();
            System.out.println(" learned !");
        }
        if (jlevel == 10) { //level 10 passive skill
            toLevel.addPassive(new ());
            toLevel.printName();
            System.out.println(" learned the passive skill !");
        }*/
        return "";
    }

    @Override
    public boolean canUseHeavyArmor() {
        return true;
    }
}
