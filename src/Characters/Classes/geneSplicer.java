package Characters.Classes;

import Characters.Skills.secondClass.geneSplicerCaperEmissarius;
import Characters.Skills.secondClass.geneSplicerCreateHomunculus;
import Characters.Skills.secondClass.geneSplicerGenomeBinding;
import Characters.Skills.secondClass.geneSplicerSphaeraeEversioSui;
import Characters.playerCharacter;
import Structures.orderedDLLNode;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class geneSplicer extends characterClass{
    public geneSplicer(){
        super("Gene Splicer", 10000);
        Skills.Insert(new orderedDLLNode(new geneSplicerCreateHomunculus())); //initial skill
    }

    @Override
    public void baseDing(playerCharacter toLevel, int level) {
        if(level % 2 == 0) {
            toLevel.incrementStat(4, 2); //add two int
            toLevel.incrementStat(5, -1); //lose one faith
        }
        else
            toLevel.incrementStat(1, 2); //add two dex
    }

    @Override
    public void jobDing(playerCharacter toLevel) {
        if (jlevel == 2) { //level 2 active skill
            Skills.Insert(new orderedDLLNode(new geneSplicerSphaeraeEversioSui()));
            toLevel.printName();
            System.out.println(" learned Create Sphaerae Eversio Sui!");
        }
        if (jlevel == 4) { //level 4 active skill
            Skills.Insert(new orderedDLLNode(new geneSplicerCaperEmissarius()));
            toLevel.printName();
            System.out.println(" learned Create Caper Emissarius!");
        }
        /*if (jlevel == 6) { //level 6 active skill
            Skills.Insert(new orderedDLLNode(new ()));
            toLevel.printName();
            System.out.println(" learned !");
        }*/
        if (jlevel == 8) { //level 8 active skill
            Skills.Insert(new orderedDLLNode(new geneSplicerGenomeBinding()));
            toLevel.printName();
            System.out.println(" learned Genome Binding!");
        }
        /*if (jlevel == 10) { //level 10 passive skill
            toLevel.addPassive(new ());
            toLevel.printName();
            System.out.println(" learned the passive skill !");
        }*/
    }

    @Override
    public boolean canUseHeavyArmor() {
        return false;
    }
}
