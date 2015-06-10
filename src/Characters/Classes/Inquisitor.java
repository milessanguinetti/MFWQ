package Characters.Classes;

import Characters.Skills.firstClass.*;
import Characters.playerCharacter;
import Structures.LLLnode;
import Structures.orderedDLLNode;

/**
 * Created by Miles Sanguinetti on 5/4/15.
 */
//essentially a faith-based hybrid support/damage dealer
public class Inquisitor extends characterClass{
    public Inquisitor(){
        super("Inquisitor");
        Skills.Insert(new orderedDLLNode(new inquisitorSoothingLight()));
    }

    @Override
    public void baseDing(playerCharacter toLevel, int level) {
        if(level % 2 == 0)
            toLevel.incrementStat(0, 5); //add a faith
        else
            toLevel.incrementStat(3, 1); //add a vit
    }

    @Override
    public void jobDing(playerCharacter toLevel) {
        if(jlevel == 2){
            Skills.Insert(new orderedDLLNode(new inquisitorArdensLux()));
            toLevel.printName();
            System.out.println(" learned Ardens Lux!");
        }
        if(jlevel == 4){ //level 4 active skill
            Skills.Insert(new orderedDLLNode(new inquisitorHymnOfHealing()));
            toLevel.printName();
            System.out.println(" learned Hymn of Healing!");
        }
        if(jlevel == 6){ //level 6 active skill
            Skills.Insert(new orderedDLLNode(new inquisitorAbsolution()));
            toLevel.printName();
            System.out.println(" learned Absolve!");
        }
        if(jlevel == 8){ //level 8 active skill
            Skills.Insert(new orderedDLLNode(new inquisitorExpellereSpiritusImmundus()));
            toLevel.printName();
            System.out.println(" learned Expellere Spiritus Immundus!");
        }
        if(jlevel == 10){ //level 10 passive skill NYI
            toLevel.addPassive(null);
            toLevel.printName();
            System.out.println(" learned !");
        }
        if(jlevel == 12){ //level 12 active skill
            Skills.Insert(new orderedDLLNode(new inquisitorCorpusBenedictus()));
            toLevel.printName();
            System.out.println(" learned Corpus Benedictus!");
        }
        if(jlevel == 14){ //level 14 active skill
            Skills.Insert(new orderedDLLNode(new inquisitorAnimusBenedictus()));
            toLevel.printName();
            System.out.println(" learned Animus Benedictus!");
        }
        if(jlevel == 16){ //level 16 active skill
            Skills.Insert(new orderedDLLNode(new inquisitorDecretum()));
            toLevel.printName();
            System.out.println(" learned Decretum!");
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
        return false;
    }
}
