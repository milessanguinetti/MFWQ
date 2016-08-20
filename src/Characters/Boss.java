package Characters;

import Profile.Game;

/**
 * Created by Spaghetti on 8/16/2016.
 */
public abstract class Boss extends Monster {
    public Boss(String toName, Stats toCopy){
        super(toName, toCopy); //copy stats
        hasBeenStolenFrom = true; //ensure that we cannon steal from a boss monster.
    }

    public Boss(String toName, int hp, int sp, int str, int dex, int spd, int vit, int inte, int fth, int arm){
        super(toName, hp, sp, str, dex, spd, vit, inte, fth, arm);
        hasBeenStolenFrom = true;
    }

    @Override
    public void updateAnimation(){
        if(isAlive()) //perform the same function as any other monster
            setWaiting();
        else {
            setDead();
            Game.currentMap.removeBoss(); //remove the boss's icon from the current map.
        }
    }
}
