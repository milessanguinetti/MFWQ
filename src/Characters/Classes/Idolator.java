package Characters.Classes;

import Characters.Skills.secondClass.idolatorConvert;
import Characters.Skills.secondClass.idolatorGruesomeTransformation;
import Characters.Skills.secondClass.idolatorProfaneBlade;
import Characters.Skills.secondClass.idolatorThunderHammer;
import Characters.playerCharacter;
import Structures.LLLnode;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
public class Idolator extends characterClass{
    public Idolator(){
        super("Idolator", 10000);
        Skills.Insert(new LLLnode(new idolatorProfaneBlade())); //initial skill
    }

    @Override
    public void baseDing(playerCharacter toLevel, int level) {
        if(level % 2 == 0) {
            toLevel.incrementStat(0, 2); //add two str
            toLevel.incrementStat(1, -1); //lose one dex
        }
        else
            toLevel.incrementStat(5, 2); //add two faith
    }

    @Override
    public void jobDing(playerCharacter toLevel) {
        if (jlevel == 2) { //level 2 active skill
            Skills.Insert(new LLLnode(new idolatorGruesomeTransformation()));
            toLevel.printName();
            System.out.println(" learned Gruesome Transformation!");
        }
        if (jlevel == 4) { //level 4 active skill
            Skills.Insert(new LLLnode(new idolatorThunderHammer()));
            toLevel.printName();
            System.out.println(" learned Thunder Hammer!");
        }
        /*if (jlevel == 6) { //level 6 active skill
            Skills.Insert(new LLLnode(new ()));
            toLevel.printName();
            System.out.println(" learned !");
        }*/
        if (jlevel == 8) { //level 8 active skill
            Skills.Insert(new LLLnode(new idolatorConvert()));
            toLevel.printName();
            System.out.println(" learned Convert!");
        }
        /*if (jlevel == 10) { //level 10 passive skill
            toLevel.addPassive(new ());
            toLevel.printName();
            System.out.println(" learned the passive skill !");
        }*/
    }

    @Override
    public boolean canUseHeavyArmor() {
        return true;
    }
}
