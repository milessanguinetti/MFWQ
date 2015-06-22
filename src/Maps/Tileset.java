package Maps;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Created by Miles on 6/11/2015.
 */
public class Tileset {
    String Name; //the name of the tileset in question
    Random Rand = new Random(); //RNG for construction of fairly random

    /*
    the following functions return imageviews based on the function called and the name of the tileset.
    some contain RNG elements, whereas others utilize a direction to rotate the tile that we're retrieving.
    0 = north or NE
    1 = east or SE
    2 = south or SW
    3 = west or NW
     */

    ImageView getExit(int Direction){
        try(InputStream imginput = Files.newInputStream(Paths.get(Name + "exit.png"))){
            ImageView toReturn = new ImageView(new Image(imginput));
            toReturn.setFitWidth(96);
            toReturn.setFitHeight(96); //preserve aspect ratio
            return toReturn;
        }

        catch (IOException e){
            System.out.println("Error loading image.");
            return null;
        }
    }

    ImageView getWall(int Direction){
        int which = Rand.nextInt(3) + 1;
        try(InputStream imginput = Files.newInputStream(Paths.get(Name + "wall" + which + ".png"))){
            ImageView toReturn = new ImageView(new Image(imginput));
            toReturn.setFitWidth(96);
            toReturn.setFitHeight(96); //preserve aspect ratio
            return toReturn;
        }

        catch (IOException e){
            System.out.println("Error loading image.");
            return null;
        }
    }

    ImageView getCorner(int Direction){
        try(InputStream imginput = Files.newInputStream(Paths.get(Name + "corner.png"))){
            ImageView toReturn = new ImageView(new Image(imginput));
            toReturn.setFitWidth(96);
            toReturn.setFitHeight(96); //preserve aspect ratio
            return toReturn;
        }

        catch (IOException e){
            System.out.println("Error loading image.");
            return null;
        }
    }

    ImageView getGround(){
        int which = Rand.nextInt(6) + 1;
        try(InputStream imginput = Files.newInputStream(Paths.get(Name + "ground" + which + ".png"))){
            ImageView toReturn = new ImageView(new Image(imginput));
            toReturn.setFitWidth(96);
            toReturn.setFitHeight(96); //preserve aspect ratio
            return toReturn;
        }

        catch (IOException e){
            System.out.println("Error loading image.");
            return null;
        }
    }

    ImageView getTreasure(){
        try(InputStream imginput = Files.newInputStream(Paths.get(Name + "treasure.png"))){
            ImageView toReturn = new ImageView(new Image(imginput));
            toReturn.setFitWidth(96);
            toReturn.setFitHeight(96); //preserve aspect ratio
            return toReturn;
        }

        catch (IOException e){
            System.out.println("Error loading image.");
            return null;
        }
    }

    ImageView getObstacle(int Which){
        /*
        1 = 1x2
        2 = 2x2
        3 = 3x2
         */
        String addendum = null;
        if(Which == 1){
            addendum = "obst1x2.png";
        }
        else if(Which == 2){
            int roll = Rand.nextInt(2);
            if(roll == 0)
                addendum = "obst2x2.png";
            else
                addendum = "obst2x2(2).png";
        }
        else if(Which == 3){
            addendum = "obst3x2.png";
        }

        if(addendum == null){
            return null;
        }

        try(InputStream imginput = Files.newInputStream(Paths.get(Name + addendum))){
            ImageView toReturn = new ImageView(new Image(imginput));
            toReturn.setFitWidth(96);
            toReturn.setFitHeight(96); //preserve aspect ratio
            return toReturn;
        }

        catch (IOException e){
            System.out.println("Error loading image.");
            return null;
        }

    }
}
