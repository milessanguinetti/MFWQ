package Profile;

import Characters.Inventory.Item;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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

    public Notification(){
        Title.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 100));
        Title.setFill(Color.GOLD);
        Title.setTranslateY(-200);
        Description.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 30));
        Description.setFill(Color.GOLD);
        Description.setTranslateY(200);
        Description.setWrappingWidth(600); //wrap text if need be
        setPrefSize(1280, 800);
        setVisible(false); //initialize object as invisible
        javafx.scene.shape.Rectangle Background = new javafx.scene.shape.Rectangle(1280, 800);
        Background.setFill(Color.BLACK);
        Background.setOpacity(.3);
        javafx.scene.shape.Rectangle Background2 = new javafx.scene.shape.Rectangle(800, 600);
        Background2.setFill(Color.BLACK);
        setAlignment(Pos.CENTER);
        getChildren().addAll(Background, Background2, Title, Description);
    }

    //loot notification for an array (SIZE 4) of items
    public void lootNotification(Item[]toAdd){
        lootcontainer = new lootContainer();
        getChildren().add(lootcontainer);
        int count = 0;
        for(int i = 0; i < 4; ++i){
            if(toAdd[i] != null){
                Game.Player.Insert(toAdd[i]);
                lootcontainer.addItem(toAdd[i]);
                ++count;
            }
        }
        if(count != 0) { //if at least one item dropped...
            Game.mainmenu.getCurrentGame().setDelay(count * 500); //set a delay on any processed user input
            setVisible(true); //set the notification pane to visible
        }


    }

    //loot notification for a single items
    public void lootNotification(Item toAdd){
        lootcontainer = new lootContainer();
        getChildren().add(lootcontainer);
        lootcontainer.addItem(toAdd);
        Game.Player.Insert(toAdd);
        Game.mainmenu.getCurrentGame().setDelay(500); //set a delay on any processed user input
        setVisible(true); //set the notification pane to visible
    }

    public boolean handleInput(){
        if(Game.mainmenu.getCurrentGame().isDelayOver()) {
            if (isVisible()) {
                getChildren().removeAll(lootcontainer); //remove any extant elements for next usage.
                Description.setVisible(false);
                setVisible(false);
                return true; //denote that this erased a notification or ran into the delay.
            }
            return false; //denote that this didn't do anything.
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
            getChildren().add(new itemBox(toAdd));
            ++itemSum;
        }
    }

    //class strictly for holding a loot icon
    private static class itemBox extends StackPane {
        private ImageView backGround = null;
        private ImageView Icon;
        private Item contents;
        private Text itemName = new Text();

        public itemBox(Item Contents){
            contents = Contents;
            itemName.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 30));
            itemName.setFill(Color.GRAY);
            itemName.setTranslateY(-100);
            itemName.setText(contents.returnKey());
            Icon = contents.getIcon();
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

            setPlain(); //default to plain

            //if the user enters any component of the image, set to highlit; set to plain if they leave.
            backGround.setOnMouseEntered(event -> {
                setHighLit();
            });

            backGround.setOnMouseExited(event -> {
                setPlain();
            });
            Icon.setOnMouseEntered(event -> {
                setHighLit();
            });

            Icon.setOnMouseExited(event -> {
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
