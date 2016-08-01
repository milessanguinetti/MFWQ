package Characters.Skills.Passive;

import Characters.gameCharacter;
import Structures.Data;
import sun.security.krb5.internal.crypto.Des;

import java.io.PrintWriter;
import java.io.Serializable;

/**
 * Created by Miles Sanguinetti on 4/2/15.
 */
public abstract class passiveSkill implements Data, Serializable{
    String skillName;
    String Description;

    //default constructor
    passiveSkill(){}

    //special constructor
    passiveSkill(String passedName, String passedDescription){
        skillName = passedName;
        Description = passedDescription;
    }

    public abstract void passiveEffect(gameCharacter toEffect);

    public String getSkillName(){
        return skillName;
    }

    public String getDescription(){
        return Description;
    }

    @Override
    public void Display() {
        System.out.println(skillName + ':');
        System.out.println(Description);
    }

    @Override
    public void Display(int indent) {
        for(int i = 0; i < indent; ++i){
            System.out.print("      ");
        }
        System.out.println(skillName + ':');
        for(int i = 0; i < indent; ++i){
            System.out.print("      ");
        }
        System.out.println(Description);
    }

    @Override
    public String returnKey() {
        return skillName;
    }

    @Override
    public void setKey(String name) {
        skillName = name;
    }

    @Override
    public int compareTo(String toCompare) {
        return skillName.compareTo(toCompare);
    }

    @Override
    public int compareTo(Data toCompare) {
        return skillName.compareTo(toCompare.returnKey());
    }

    @Override
    public void writeOut(PrintWriter toWrite) {
        toWrite.println(skillName);
    }
}
