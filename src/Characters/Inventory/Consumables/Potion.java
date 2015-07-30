package Characters.Inventory.Consumables;

import Characters.Inventory.Consumable;
import Characters.gameCharacter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
public class Potion extends Consumable{
    public Potion(){
        super("Potion", "A red draught that heals 50HP. Consumed after use.");
    }

    @Override
    public boolean Use(gameCharacter toUseOn) {
        toUseOn.takeAbsoluteDamage(-50); //heal 50 damage
        //NOT FULLY IMPLEMENTED.
        return true;
    }

    @Override
    public boolean notUsableOnDead(){
        return true; //not usable on dead characters
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean isOffensive() {
        return false;
    }

    @Override
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeAbsoluteDamage(-50);
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
