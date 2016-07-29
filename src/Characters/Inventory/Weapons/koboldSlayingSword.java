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
 * Created by Miles Sanguinetti on 4/27/15.
 */
public class koboldSlayingSword extends Weapon{
    public koboldSlayingSword(){
        super("Kobold Slaying Sword",
                "A battle-worn sword that has slain countless kobolds throughout its years.",
                6, "1h Edged", "Fire");
    }

    public koboldSlayingSword(int Damage){
        super("Kobold Slaying Sword",
                "A battle-worn sword that has slain countless kobolds throughout its years.",
                5+(Damage/5)*5, "1h Edged", "Fire");
        if(Math.round(Math.floor((Damage*5)/5f)) != 0)
            itemName += ("+" + (Math.round(Math.floor(Damage/5f)))); //to give us a damage-based qualifier to add
    }

    @Override
    public boolean isRightHand(){
        return true;
    }

    @Override
    public boolean isTwoHand(){
        return false;
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
        if(Roll == 0){ //20% of the time, the attack does extra fire damage
            Game.battle.getInterface().printLeftAtNextAvailable("A critical hit!");
            Defender.takeDamage(Math.round(1.25f * (Damage + Caster.getTempStr())), "Fire");
        }
        else{ //but most of the time it just does neutral damage
            Defender.takeDamage(Math.round(.75f *(Damage + Caster.getTempStr())), "Neutral");
        }
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        return; //this weapon causes no SP loss.
    }

    //equippable item methods
    @Override
    public void applyBuffs(playerCharacter toBuff) {
        return; //this weapon applies no buffs.
    }

    @Override //this weapon adds one to strength.
    public void addStats(playerCharacter toAdd) {
        toAdd.incrementStat(0, 1);
    }

    @Override
    public void subtractStats(playerCharacter toSubtract) {
        toSubtract.incrementStat(0, -1);
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
