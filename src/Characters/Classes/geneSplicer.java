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
    public String getClassDescription(){
        return "At the vanguard of scientific achievement, the gene splicer is peerless in their knowledge of homunculi";
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
    public String jobDing(playerCharacter toLevel) {
        if (jlevel == 2) { //level 2 active skill
            Skills.Insert(new orderedDLLNode(new geneSplicerSphaeraeEversioSui()));
            return (toLevel.getName() +" learned Create Sphaerae Eversio Sui!");
        }
        if (jlevel == 4) { //level 4 active skill
            Skills.Insert(new orderedDLLNode(new geneSplicerCaperEmissarius()));
            return (toLevel.getName() +" learned Create Caper Emissarius!");
        }
        /*if (jlevel == 6) { //level 6 active skill
            Skills.Insert(new orderedDLLNode(new ()));
            return (toLevel.getName() +" learned !");
        }*/
        if (jlevel == 8) { //level 8 active skill
            Skills.Insert(new orderedDLLNode(new geneSplicerGenomeBinding()));
            return (toLevel.getName() +" learned Genome Binding!");
        }
        /*if (jlevel == 10) { //level 10 passive skill
            toLevel.addPassive(new ());
            return (toLevel.getName() +" learned the passive skill !");
        }*/
        return "";
    }


    @Override
    public boolean canUseHeavyArmor() {
        return false;
    }
}
