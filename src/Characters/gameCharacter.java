package Characters;

import Characters.Skills.passiveSkill;

/**
 * Created by Miles Sanguinetti on 3/17/15.
 */

//abstract character base class--derived from stats
public abstract class gameCharacter extends Stats {
    private String Name; //character name
    protected passiveSkill currentPassive; //the character's passive skill.

    //default constructor
    public gameCharacter(){}

    //constructor with name
    public gameCharacter(String toName){
        Name = toName;
    }

    //constructor with name and stats
    public gameCharacter(String toName, Stats toAdd){
        super(toAdd);
        Name = toName;
    }

    //print name, but not stats
    public void printName(){
        System.out.println(Name);
    }

    //full display function
    public void Display(){
        System.out.println(Name); //print the character's name
        super.Display(); //display the character's stats, too
    }
}
