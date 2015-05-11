package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
//special constructor with a game character matches whoever has the buff's
//temp stats with any stats that whoever was passed in has superior stats of.
public class matchBestStats extends statusEffectData implements statChange{
    gameCharacter toMatch; //whoever we are matching stats with.

    public matchBestStats(gameCharacter tomatch){
        super("zzzMatchBestStats", 200); //lasts 200 turns, effectively an infinite buff.
                                         //zzz places it at the end of the status effect list
                                         //so that it doesn't stack with other buffs.
        toMatch = tomatch;
    }

    @Override
    public void changeStats(gameCharacter toAffect) {
        if(toMatch.getTempStr() > toAffect.getTempStr())
            toAffect.setTempStr(toMatch.getTempStr());
        if(toMatch.getTempDex() > toAffect.getTempDex())
            toAffect.setTempDex(toMatch.getTempDex());
        if(toMatch.getTempSpd() > toAffect.getTempSpd())
            toAffect.setTempSpd(toMatch.getTempSpd());
        if(toMatch.getTempFth() > toAffect.getTempFth())
            toAffect.setTempFth(toMatch.getTempFth());
        if(toMatch.getTempInt() > toAffect.getTempInt())
            toAffect.setTempInt(toMatch.getTempInt());
    }
}
