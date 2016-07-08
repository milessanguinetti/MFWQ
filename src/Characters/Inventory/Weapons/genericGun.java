package Characters.Inventory.Weapons;

import Characters.Inventory.Weapon;
import Characters.gameCharacter;
import Characters.playerCharacter;
import Profile.Game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class genericGun extends Weapon{
    public genericGun(){}

    public genericGun(int damage){
        super("A standard gun with little remarkable about it.",
                3+(damage/5)*5, "Gun"); //ensure that damage is a multiple of 5.

        Random Rand = new Random();
        int Roll = Rand.nextInt(18); //a roll to determine a number of variables
        int Quality = Roll % 3; //minor damage variation based on roll
        if(Quality == 0) {
            itemName = "Rusty ";
            --Damage;
        }
        else if(Quality == 1){
            itemName = "";
        }
        else{
            itemName = "Gleaming ";
            ++Damage;
        }
        if(Roll > 5){ //neutral case
            Property = "Neutral"; //set property to neutral
        }
        else if(Roll == 0){ //fire case
            if(Quality == 2)
                itemName += "Explosive ";
            else
                itemName += "Fiery ";
            Property = "Fire";
        }
        else if(Roll == 1){ //water case
            if(Quality == 2)
                itemName += "Torrential ";
            else
                itemName += "Liquid ";
            Property = "Water";
        }
        else if(Roll == 2){ //holy case
            if(Quality == 2)
                itemName += "Hallowed ";
            else
                itemName += "Blessed ";
            Property = "Holy";
        }
        else if(Roll == 3){ //unholy case
            if(Quality == 2)
                itemName += "Profane ";
            else
                itemName += "Cursed ";
            Property = "Unholy";
        }
        else if(Roll == 4){ //undead case
            if(Quality == 2)
                itemName += "Lichborne ";
            else
                itemName += "Ghoulish ";
            Property = "Undead";
        }
        else { //organic case
            if(Quality == 2)
                itemName += "Verdant ";
            else
                itemName += "Overgrown ";
            Property = "Organic";
        }
        int subType = Roll % 6; //things like saber v.s. mace that are purely for flavor
        if(subType == 0)
            itemName += "Flintlock";
        else if(subType == 1)
            itemName += "Pistol";
        else if(subType == 2)
            itemName += "Hand Cannon";
        else if(subType == 3)
            itemName += "Revolver";
        else if(subType == 4)
            itemName += "Gun";
        else
            itemName += "Matchlock";
        if(Damage/5 != 0)
            itemName += '+' + Damage/5; //to give us a damage-based qualifier to add
        //more insight into the weapon's strength.
    }

    //combat effect methods
    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return true;
    }

    @Override
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Random Rand = new Random();
        int Roll = Rand.nextInt(5);
        if(Roll == 0){ //20% of the time, the attack does extra damage
            Game.battle.getInterface().printLeftAtNextAvailable("A critical hit!");
            Defender.takeDamage(Math.round(1.25f*(Damage + Caster.getTempStr())), Property);
        }
        else{ //but most of the time it just does neutral damage
            Defender.takeDamage(Math.round(.75f*(Damage + Caster.getTempStr())), Property);
        }
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        //this weapon causes no SP loss.
    }

    //equippable item methods
    @Override
    public void applyBuffs(playerCharacter toBuff) {
        //this weapon applies no buffs.
    }

    @Override
    public void addStats(playerCharacter toAdd) {
        //this weapon adds no stats.
    }

    @Override
    public void subtractStats(playerCharacter toSubtract) {
        //this weapon adds no stats.
    }

    @Override
    public ImageView getIcon(){
        try(InputStream imginput = Files.newInputStream(Paths.get("resources/thumbnails/placeholder.png"))){
            ImageView Icon = new ImageView(new Image(imginput));
            Icon.setFitWidth(96);
            Icon.setFitHeight(96); //preserve aspect ratio
            return Icon;
        }

        catch (IOException e){
            System.out.println("Error loading" + itemName + " icon.");
        }
        return null;
    }
}
