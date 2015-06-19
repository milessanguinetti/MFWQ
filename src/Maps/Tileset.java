package Maps;

import javafx.scene.image.ImageView;

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

    }

    ImageView getWall(int Direction){

    }

    ImageView getCorner(int Direction){

    }

    ImageView getGround(){

    }

    ImageView getTreasure(){

    }

    ImageView getObstacle(int Which){
        /*
        1 = 1x2
        2 = 2x2
        3 = 3x2
         */

    }
}
