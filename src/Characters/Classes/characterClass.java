package Characters.Classes;

import Characters.Skills.Skill;
import Characters.gameCharacter;
import Structures.LLLnode;
import Structures.orderedLLL;

/**
 * Created by Miles Sanguinetti on 3/21/15.
 */
public abstract class characterClass {
    String className; //the name of the class
    int jlevel = 1;
    int jexp = 0; //job experience and job experience need to reach next level.
    int jexpCap = 1000;
    orderedLLL Skills; //skills for this class

    //add job experience to the character's class.
    public void gainJexp(int Gains, gameCharacter toLevel){
        if((jexp + Gains) >= jexpCap){ //if we've received enough jexp to level...
            Gains -= (jexpCap - jexp);
            ++jlevel;
            jexpCap += Math.round(jexpCap*1.2); //jexp cap goes up by 20%
            jexp = 0; //reset jexp to 0 now that we've leveled.
            Ding(toLevel);
            gainJexp(Gains, toLevel); //recursive call to add the rest of the jexp onto the
                             //next level.
        }
        else
            jexp += Gains; //otherwise, we just add it to our exp.
    }

    //adds skills and stat bonuses according to the class in question
    public abstract void Ding(gameCharacter toLevel);

    public Skill getSkill(int toGet){
        LLLnode Retrieved = Skills.retrieveInt(toGet);
        if(Retrieved == null)
            return null;
        return ((Skill)Retrieved.returnData());
    }

    public void displaySkills(){
        Skills.displayNumbered();
    }
}
