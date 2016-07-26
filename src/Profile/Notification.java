package Profile;

import Characters.Inventory.Item;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Miles on 7/29/2015.
 */
public class Notification extends StackPane implements Serializable{
    private static Text Title = new Text();
    private static Text Description = new Text();
    private lootContainer lootcontainer;
    private boolean active = false;
    private Item [] loot;

    public Notification(){
        Title.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 100));
        Title.setFill(Color.GOLD);
        Title.setTranslateY(-200);
        Description.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 30));
        Description.setFill(Color.GOLD);
        Description.setTranslateY(200);
        Description.setWrappingWidth(600); //wrap text if need be
        //setVisible(false); //initialize object as invisible
        javafx.scene.shape.Rectangle Background = new javafx.scene.shape.Rectangle(3000, 3000);
        Background.setFill(Color.BLACK);
        Background.setOpacity(.3);
        javafx.scene.shape.Rectangle Background2 = new javafx.scene.shape.Rectangle(800, 600);
        Background2.setFill(Color.BLACK);
        setAlignment(Pos.CENTER);
        getChildren().addAll(Background, Background2, Title, Description);
    }

    //loot notification for an array (SIZE 4) of items
    public void lootNotification(Item[]toAdd){
        loot = toAdd;
    }

    //loot notification for a single items
    public void lootNotification(Item toAdd){
        loot = new Item[4];
        loot[0] = toAdd;
    }

    public void Animate(){
        lootcontainer = new lootContainer();
        getChildren().add(lootcontainer);
        int count = 0;
        for(int i = 0; i < 4; ++i){
            if(loot[i] != null){
                Game.Player.Insert(loot[i]);
                lootcontainer.addItem(loot[i]);
                loot[i] = null;
                ++count;
            }
        }
        if(count != 0) { //if at least one item dropped...
            active = true;
            Game.mainmenu.getCurrentGame().setDelay(count * 1000); //set a delay on any processed user input
        }
    }

    public boolean isActive(){
        return active;
    }

    public boolean handleInput(){
        if(!active)
            return false;
        if(Game.mainmenu.getCurrentGame().isDelayOver()) {
            if (active) {
                getChildren().removeAll(lootcontainer); //remove any extant elements for next usage.
                //Description.setVisible(false);
                Game.mainmenu.getCurrentGame().removeNotifications();
                active = false;
            }
        }
        return true; //denote that this erased a notification or ran into the delay.
    }

    //subclasses for notifications
    //class for displaying loot graphics.
    private static class lootContainer extends HBox {
        private int itemSum = 0;
        public lootContainer() {
            Title.setText("Loot");
            setAlignment(Pos.CENTER);
            setSpacing(10);
        }

        public void addItem(Item toAdd){
            getChildren().add(new itemBox(toAdd, itemSum));
            ++itemSum;
        }
    }

    //class strictly for holding a loot icon
    private static class itemBox extends StackPane {
        private ImageView backGround = null;
        private ImageView Icon;
        private Item contents;
        private Text itemName = new Text();

        public itemBox(Item Contents, int itemNumber){
            contents = Contents;
            itemName.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 30));
            itemName.setFill(Color.GRAY);
            itemName.setTranslateY(-100);
            itemName.setText(contents.returnKey());
            itemName.setOpacity(0);
            Icon = contents.getIcon();
            Icon.setOpacity(0);
            try(InputStream imginput = Files.newInputStream(Paths.get("resources/thumbnails/lootslot.jpg"))){
                ImageView background = new ImageView(new Image(imginput));
                background.setFitWidth(110);
                background.setFitHeight(110); //preserve aspect ratio
                backGround = background;
            }
            catch (IOException e){
                System.out.println("Error loading loot slot image.");
            }
            setAlignment(Pos.CENTER); //center the box's sub-components.
            getChildren().addAll(backGround, Icon, itemName); //add the background and icon to the button.

            //phase name and icon in sequentially using a sequential transition...
            PauseTransition ptN = new PauseTransition(Duration.seconds(itemNumber)); //pause transition for name
            PauseTransition ptI = new PauseTransition(Duration.seconds(itemNumber)); //pause transition for icon
            FadeTransition ftN = new FadeTransition(Duration.seconds(1)); //fade transition for name
            ftN.setFromValue(0);
            ftN.setToValue(.9);
            FadeTransition ftI = new FadeTransition(Duration.seconds(1)); //fade transition for icon
            ftI.setFromValue(0);
            ftI.setToValue(.9);
            SequentialTransition stN = new SequentialTransition(itemName, ptN, ftN); //sequential transition for name
            SequentialTransition stI = new SequentialTransition(Icon, ptI, ftI); //sequential transition for icon
            Timeline lootSound = new Timeline(new KeyFrame( //loot sound effects
                    Duration.seconds(itemNumber + 1),
                    ae -> {
                        Game.battle.playMedia("loot");
                        Rectangle lootGlow = new Rectangle(110, 110);
                        lootGlow.setFill(Color.DARKGOLDENROD);
                        lootGlow.setOpacity(0);
                        getChildren().addAll(lootGlow);
                        FadeTransition ftG = new FadeTransition(Duration.seconds(.35), lootGlow); //fade lootglow
                        ftG.setCycleCount(2); //ensure that the transition reverses itself.
                        ftG.setFromValue(0);
                        ftG.setToValue(.6);
                        ftG.setAutoReverse(true);
                        ftG.play();
                    }));
            lootSound.play(); //play all transitions and timelines
            stN.play();
            stI.play();


            //if the user enters any component of the image, set to highlit; set to plain if they leave.
            backGround.setOnMouseEntered(event -> {
                if(Game.mainmenu.getCurrentGame().isDelayOver())
                    setHighLit();
            });

            backGround.setOnMouseExited(event -> {
                if(Game.mainmenu.getCurrentGame().isDelayOver())
                    setPlain();
            });
            Icon.setOnMouseEntered(event -> {
                if(Game.mainmenu.getCurrentGame().isDelayOver())
                    setHighLit();
            });

            Icon.setOnMouseExited(event -> {
                if(Game.mainmenu.getCurrentGame().isDelayOver())
                    setPlain();
            });
        }

        public void setHighLit(){ //makes the image/text more striking
            backGround.setOpacity(1);
            Icon.setOpacity(1);
            itemName.setFill(Color.WHITE);
            Description.setText(contents.getDescription());
            Description.setVisible(true);
        }

        public void setPlain() { //self-explanatory
            backGround.setOpacity(.9);
            Icon.setOpacity(.9);
            itemName.setFill(Color.GRAY);
            Description.setVisible(false);
        }
    }
}
