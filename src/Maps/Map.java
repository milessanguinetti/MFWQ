package Maps;

import Characters.Inventory.Item;
import Characters.Inventory.Weapons.*;
import Characters.Monster;
import Profile.Game;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Miles Sanguinetti on 5/20/2015.
 */
public abstract class Map extends Tileset{
    private StackPane sceneRoot = new StackPane();
    private StackPane contentRoot = new StackPane();
    protected int xBound, yBound; //the x and y bounds of the map in question
    protected Room [] Rooms; //array of rooms contained within the map
    private Map [] Connections = new Map[4]; //connections
    protected int [] connectionRooms = new int[]{-1, -1, -1, -1}; //rooms that lead in from connections
    private int currentRoom;
    private int encounterRate; //rate at which enemies are encountered; expressed as a percentage integer
    protected static int Difficulty;

    public Map(){}

    public Map(String name, int xbound, int ybound, int encounterrate, Map North, Map East, Map South, Map West){
        encounterRate = encounterrate;
        contentRoot.setAlignment(Pos.CENTER);
        //contentRoot.setTranslateX(-200);
        sceneRoot.setAlignment(Pos.CENTER);
        sceneRoot.getChildren().add(contentRoot);
        //sceneRoot.getChildren().add(Game.notification);
        Name += name;
        try(InputStream imginput = Files.newInputStream(Paths.get(Name + "background.jpg"))){
            setImage(new Image(imginput)); //set battle background image to this
            setFitWidth(1280);             //imageview for calls from battle object.
            setFitHeight(600);
        }

        catch (IOException e){
            System.out.println("Error loading " + Name + "'s battle background.");
        }
        xBound = xbound;
        yBound = ybound;
        Rooms = new Room[xBound * yBound]; //allocate rooms
        Connections = new Map[4]; //store connections in array
        Connections[0] = North;
        Connections[1] = East;
        Connections[2] = South;
        Connections[3] = West;


        Build(); //call the abstract build method to begin constructing the map.
        Room.currentMap = this;
        Fill(); //call fill to fill in any blanks left by the build method

        //key released listening code
        sceneRoot.setOnKeyReleased(event1 -> {
            if(Game.notification.handleInput())
                return;
            //ENTER CASE
            if (event1.getCode() == KeyCode.ENTER) {
                switch (Rooms[currentRoom].Interact()) {
                    case 0: { //just break; nothing happened
                        break;
                    }
                    case 1: { //exit to north
                        contentRoot.getChildren().remove(Rooms[currentRoom]); //remove this room's graphics
                        if (currentRoom - yBound < 0) { //exited beyond the scope of the map
                            if (Connections[0] != null) {
                                Game.currentMap = Connections[0];
                                Connections[0].Enter(3);
                                Game.mainmenu.getCurrentGame().swapToMap(sceneRoot);
                            } else
                                Game.mainmenu.getCurrentGame().swapToOverworld(sceneRoot);
                        } else { //a simple change of rooms within the map
                            currentRoom -= yBound; //decrement current room by y bound
                            contentRoot.getChildren().add(Rooms[currentRoom]); //add the one we're entering's
                            Rooms[currentRoom].Enter(3); //enter the new room from the south
                        }
                        break;
                    }
                    case 2: { //exit to east
                        contentRoot.getChildren().remove(Rooms[currentRoom]); //remove this room's graphics
                        if ((currentRoom + 1) % xBound == 0) { //exited beyond the scope of the map
                            if (Connections[1] != null) {
                                Game.currentMap = Connections[1]; //set current map to the connection
                                Connections[1].Enter(4); //enter from the west
                                Game.mainmenu.getCurrentGame().swapToMap(sceneRoot); //load the new map's scene
                            } else
                                Game.mainmenu.getCurrentGame().swapToOverworld(sceneRoot); //we have left the scope
                                // of this connected grouping of maps; swap to overworld
                        } else { //a simple change of rooms within the map
                            ++currentRoom; //increment current room by 1
                            contentRoot.getChildren().add(Rooms[currentRoom]); //add the one we're entering's
                            Rooms[currentRoom].Enter(4); //enter the new room from the west
                        }
                        break;
                    }
                    case 3: { //exit to south
                        contentRoot.getChildren().remove(Rooms[currentRoom]); //remove this room's graphics
                        if (currentRoom + yBound >= xBound * yBound) { //exited beyond the scope of the map
                            if (Connections[2] != null) {
                                Game.currentMap = Connections[1]; //set current map to the connection
                                Connections[2].Enter(1); //enter from the north
                                Game.mainmenu.getCurrentGame().swapToMap(sceneRoot); //load the new map's scene
                            } else
                                Game.mainmenu.getCurrentGame().swapToOverworld(sceneRoot); //we have left the scope
                            // of this connected grouping of maps; swap to overworld
                        } else { //a simple change of rooms within the map
                            currentRoom += yBound; //increment currentroom by yBound
                            contentRoot.getChildren().add(Rooms[currentRoom]); //add the one we're entering's
                            Rooms[currentRoom].Enter(1); //enter the new room from the north
                        }
                        break;
                    }
                    case 4: {//exit to west
                        contentRoot.getChildren().remove(Rooms[currentRoom]); //remove this room's graphics
                        if (currentRoom % xBound == 0) { //exited beyond the scope of the map
                            if (Connections[3] != null) {
                                Game.currentMap = Connections[1]; //set current map to the connection
                                Connections[3].Enter(2); //enter from the east
                                Game.mainmenu.getCurrentGame().swapToMap(sceneRoot); //load the new map's scene
                            } else
                                Game.mainmenu.getCurrentGame().swapToOverworld(sceneRoot); //we have left the scope
                            // of this connected grouping of maps; swap to overworld
                        } else { //a simple change of rooms within the map
                            --currentRoom; //decrement currentroom by 1
                            contentRoot.getChildren().add(Rooms[currentRoom]); //add the one we're entering's
                            Rooms[currentRoom].Enter(2); //enter the new room from the east
                        }
                        break;
                    }
                    case 5: { //treasure case
                        if (Rooms[currentRoom].getLoot() != null) {
                            Game.notification.lootNotification(Rooms[currentRoom].getLoot()); //add in the item
                        }
                        break;
                    }
                    case 6: { //boss case
                        if (Rooms[currentRoom].getBoss() != null) {
                            Game.battle.commenceBattle(Game.Player.getParty(), Rooms[currentRoom].getBoss());
                            Game.mainmenu.getCurrentGame().swapToBattle(sceneRoot);
                        }
                        break;
                    }
                }
            }
            else if (event1.getCode() == KeyCode.UP || event1.getCode() == KeyCode.W){
                Rooms[currentRoom].pressKey(0, false);
            }
            else if (event1.getCode() == KeyCode.RIGHT || event1.getCode() == KeyCode.D){
                Rooms[currentRoom].pressKey(1, false);
            }
            else if (event1.getCode() == KeyCode.DOWN || event1.getCode() == KeyCode.S){
                Rooms[currentRoom].pressKey(2, false);
            }
            else if (event1.getCode() == KeyCode.LEFT || event1.getCode() == KeyCode.A){
                Rooms[currentRoom].pressKey(3, false);
            }
            //ESCAPE CASE
            else if(event1.getCode() == KeyCode.ESCAPE){
                Game.mainmenu.getCurrentGame().swapToInventory(sceneRoot);
            }
        });

        //key pressed listening code
        sceneRoot.setOnKeyPressed(event -> {
            if(Game.mainmenu.getCurrentGame().isDelayOver()) {
                //UP CASE
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
                    Rooms[currentRoom].pressKey(0, true);
                }
                //RIGHT CASE
                else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                    Rooms[currentRoom].pressKey(1, true);
                }
                //DOWN CASE
                else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                    Rooms[currentRoom].pressKey(2, true);
                }
                //LEFT CASE
                else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                    Rooms[currentRoom].pressKey(3, true);
                }
            }
        });
    }

    public boolean rollForEncounter(){ //called from room objects to create random map-defined enemy encounters
        if(Rand.nextInt(100) < encounterRate){
            Game.battle.commenceBattle(Game.Player.getParty(), generateEnemies());
            Game.mainmenu.getCurrentGame().swapToBattle(sceneRoot);
            return true;
        }
        return false;
    }

    //method that procedurally generates the quintessential parts of the map in question
    public abstract void Build();

    //method that procedurally fills holes left by the build method.
    public void Fill(){
        //first, we add some extra rooms to essentially increase the randomness of the dungeon:
        int i = 0;
        for(int j = xBound * yBound/6; j > 0; --j){
            i = Rand.nextInt(xBound * yBound); //randomly generate an integer in the scope of the map
            if(Rooms[i] == null){ //if it's null, add a new room
                Rooms[i] = new Room(); //rolls on extant rooms further increase randomness
            }
        }
        //next we randomly determine whether we will fill the map north-south or east-west
        i%=2; //i is either 1 or 0.
        int firstRoom = -1; //integers to store first and last rooms encountered in a row
        int lastRoom = -1;
        if(i == 0) { //fill the map in terms of horizontal rows
            for(int k = 0; k < yBound*xBound; k+=xBound){ //ensure each row has at least one room and
                                                         //even rows have all rooms connected in a line
                for(int l = 0; l < xBound; ++l){ //code for checking each row has at least one room
                    if(Rooms[k + l] != null){ //if this room isn't empty
                        lastRoom = k+l; //set lastroom to this room
                        if(firstRoom == -1)
                            firstRoom = k+l; //if firstroom has yet to be set, handle that as well.
                    }
                }
                if(firstRoom == -1){ //if we didn't encounter a single room...
                    Rooms[k + Rand.nextInt(xBound)] = new Room(); //randomly add one.
                }
                //if the first and last rooms in this column are different and the row is even numbered...
                if(firstRoom != lastRoom && (k/xBound)%2 == 0){
                    for(int n = firstRoom + 1; n < lastRoom; ++n) //fill the space in between them
                        Rooms[n] = new Room();
                }
                firstRoom = lastRoom = -1; //reset first and last room to -1 before we move to the next row.
            }
            for(int n = xBound; n < yBound*xBound; n+=xBound*2) { //now ensure that odd rows connect to both sides.
                for (int l = 0; l < xBound; ++l) { //for the entire row...
                    if(Rooms[n -xBound + l] != null)//if a room in the previous row exists...
                        lastRoom = l; //set lastroom to l as a reference to its position
                    if(n != xBound * (yBound - 1)) { //if the next row exists...
                        if(Rooms[n +xBound + l] != null)//if a room in the next row exists...
                            firstRoom = l; //set firstroom to l as a reference to its position
                    }
                    if(Rooms[n + l] != null){ //if a room at l in the current row exists...
                        if(lastRoom == -1 || (firstRoom == -1 && n != xBound * (yBound - 1))) { //if there's a gap...
                            if (Rooms[n + l + 1] == null)
                                Rooms[n + l + 1] = new Room(); //add a room after this if it doesn't yet exist
                        }
                        else if((lastRoom < l && lastRoom != -1) || (firstRoom < l && firstRoom != -1)){
                            for(int o = Math.min(lastRoom, firstRoom); o < l; ++o ){
                             //fill in rooms so that this room has a connection to both sides.
                                if(o == -1)
                                    o = Math.max(lastRoom, firstRoom);
                                if(Rooms[n+o] == null){ //obviously don't waste memory recreating pre-existing rooms
                                    Rooms[n+o] = new Room();
                                }
                            }
                        }
                    }
                }
                lastRoom = firstRoom = -1; //reset room references
            }
        }
        else{ //fill the map in terms of vertical columns
            for(int k = 0; k < xBound; ++k){ //ensure each column has at least one room and
                //odd column have all rooms connected in a line
                for(int l = 0; l < xBound * yBound; l +=xBound){ //code for checking each column has at least one room
                    if(Rooms[k + l] != null){ //if this room isn't empty
                        lastRoom = k+l; //set lastroom to this room
                        if(firstRoom == -1)
                            firstRoom = k+l; //if firstroom has yet to be set, handle that as well.
                    }
                }
                if(firstRoom == -1){ //if we didn't encounter a single room...
                    Rooms[k + Rand.nextInt(yBound)*xBound] = new Room(); //randomly add one.
                }
                //if the first and last rooms in this column are different and the column is odd numbered...
                if(firstRoom != lastRoom && k%2 == 1){
                    for(int n = firstRoom + xBound; n < lastRoom; n+=xBound) //fill the space in between them
                        Rooms[n] = new Room();
                }
                firstRoom = lastRoom = -1; //reset first and last room to -1 before we move to the next column.
            }
            for(int n = 0; n < xBound; n+=2) { //now ensure that even columns connect to both sides.
                for (int l = 0; l < xBound*yBound; l+=xBound) { //for the entire column...
                    if(n != 0) { //if the previous column exists...
                        if(Rooms[n - 1 + l] != null)//if a room in the previous column exists...
                            lastRoom = l; //set lastroom to l as a reference to its position
                    }
                    if(n != xBound - 1) { //if the next column exists...
                        if (Rooms[n + 1 + l] != null)//if a room in the next column exists...
                            firstRoom = l; //set firstroom to l as a reference to its position
                    }
                    if(Rooms[n + l] != null){ //if a room at l in the current column exists...
                        if((lastRoom == -1 && n != 0) || (firstRoom == -1 && n != xBound - 1)) { //if there's a gap...
                            if (Rooms[n + l + xBound] == null)
                                Rooms[n + l + xBound] = new Room(); //add a room after this if it doesn't yet exist
                        }
                        else if((lastRoom < l && lastRoom != -1) || (firstRoom < l && firstRoom != -1)){
                            for(int o = Math.min(lastRoom, firstRoom); o < l; o+=xBound ){
                                //fill in rooms so that this room has a connection to both sides.
                                if(o == -1)
                                    o = Math.max(lastRoom, firstRoom);
                                if(Rooms[n+o] == null){ //obviously don't waste memory recreating pre-existing rooms
                                    Rooms[n+o] = new Room();
                                }
                            }
                        }
                    }
                }
                lastRoom = firstRoom = -1; //reset room references
            }
        }
        //by this point, we've ensured that all rooms can potentially be traversible.
        //now we remove a few unnecessary rooms with enough surrounding rooms for the map to remain traversible.
        int surroundingrooms;
        for(int j = xBound; j < xBound*(yBound-1); ++j){
            if(Rooms[j] != null) {
                if (j % yBound != 0 && j % yBound != xBound - 1 && Rooms[j].getLoot() == null) {
                    surroundingrooms = 0;
                    if (Rooms[j - 1] != null)
                        ++surroundingrooms;
                    if (Rooms[j + 1] != null)
                        ++surroundingrooms;
                    if (Rooms[j + xBound] != null)
                        ++surroundingrooms;
                    if (Rooms[j - xBound] != null)
                        ++surroundingrooms;
                    if (Rooms[j + xBound + 1] != null)
                        ++surroundingrooms;
                    if (Rooms[j - xBound + 1] != null)
                        ++surroundingrooms;
                    if (Rooms[j + xBound - 1] != null)
                        ++surroundingrooms;
                    if (Rooms[j - xBound - 1] != null)
                        ++surroundingrooms;
                    if (surroundingrooms >= 7) {
                        //System.out.println("Removing room (" + j%xBound + ", "
                        // + j/yBound + ") due to " + surroundingrooms + " surrounding rooms.");
                        Rooms[j] = null;
                    }
                    else if(surroundingrooms >= 5){
                        if(Rooms[j+xBound] != null && Rooms[j+xBound-1] != null &&
                                Rooms[j+xBound + 1] != null && Rooms[j-xBound] == null)
                            Rooms[j] = null;
                        else if(Rooms[j-xBound] != null && Rooms[j-xBound-1] != null &&
                                Rooms[j-xBound + 1] != null && Rooms[j+xBound] == null)
                            Rooms[j] = null;
                        else if(Rooms[j-1] != null && Rooms[j+xBound-1] != null &&
                                Rooms[j-xBound-1] != null && Rooms[j+1] == null)
                            Rooms[j] = null;
                        else if(Rooms[j+1] != null && Rooms[j+xBound+1] != null &&
                                Rooms[j-xBound+1] != null && Rooms[j-1] == null)
                            Rooms[j] = null;
                    }
                }
            }
        }
        boolean north = false, east = false, south = false, west = false;
        //we start by adding connection rooms, since they have exits leading beyond the map.
        for(int j = 0; j < 4; ++j){
            if(connectionRooms[j] != -1){
                if(j == 0)
                    north = true;
                else if(j == 1)
                    east = true;
                else if(j == 2)
                    south = true;
                else if(j == 3)
                    west = true;
                if(connectionRooms[j] >= xBound){ //case for checking north connection
                    if(Rooms[connectionRooms[j] - xBound] != null)
                        north = true;
                }
                if(connectionRooms[j]%xBound != xBound - 1){ //case for checking east connection
                    if(Rooms[connectionRooms[j] + 1] != null)
                        east = true;
                }
                if(connectionRooms[j] + xBound < xBound * yBound){ //case for checking south connection
                    if(Rooms[connectionRooms[j] + xBound] != null)
                        south = true;
                }
                if(connectionRooms[j]%xBound != 0){ //case for checking west connection
                    if(Rooms[connectionRooms[j] - 1] != null)
                        west = true;
                }
                if(north && !east && !south && !west)
                    Rooms[connectionRooms[j]].generateTiles(1);
                else if(!north && east && !south && !west)
                    Rooms[connectionRooms[j]].generateTiles(2);
                else if(!north && !east && south && !west)
                    Rooms[connectionRooms[j]].generateTiles(3);
                else if(!north && !east && !south && west)
                    Rooms[connectionRooms[j]].generateTiles(4);
                else if(north && !east && south && !west)
                    Rooms[connectionRooms[j]].generateTiles(5);
                else if(!north && east && !south && west)
                    Rooms[connectionRooms[j]].generateTiles(6);
                else if(north && !east && !south && west)
                    Rooms[connectionRooms[j]].generateTiles(7);
                else if(north && east && !south && !west)
                    Rooms[connectionRooms[j]].generateTiles(8);
                else if(!north && east && south && !west)
                    Rooms[connectionRooms[j]].generateTiles(9);
                else if(!north && !east && south && west)
                    Rooms[connectionRooms[j]].generateTiles(10);
                else if(!north && east && south && west)
                    Rooms[connectionRooms[j]].generateTiles(11);
                else if(north && !east && south && west)
                    Rooms[connectionRooms[j]].generateTiles(12);
                else if(north && east && !south && west)
                    Rooms[connectionRooms[j]].generateTiles(13);
                else if(north && east && south && !west)
                    Rooms[connectionRooms[j]].generateTiles(14);
                else if(north && east && south && west)
                    Rooms[connectionRooms[j]].generateTiles(15);
            }
            north = east = south = west = false; //reset booleans
        }
        //with those handled, we tackle the rest of the map.
        for(int j = 0; j < xBound * yBound; ++j){
            if(Rooms[j] != null) {
                if (j >= xBound) { //case for checking north connection
                    if (Rooms[j - xBound] != null)
                        north = true;
                }
                if (j % xBound != xBound - 1) { //case for checking east connection
                    if (Rooms[j + 1] != null)
                        east = true;
                }
                if (j + xBound < xBound * yBound) { //case for checking south connection
                    if (Rooms[j + xBound] != null)
                        south = true;
                }
                if (j % xBound != 0) { //case for checking west connection
                    if (Rooms[j - 1] != null)
                        west = true;
                }
                if (north && !east && !south && !west)
                    Rooms[j].generateTiles(1);
                else if (!north && east && !south && !west)
                    Rooms[j].generateTiles(2);
                else if (!north && !east && south && !west)
                    Rooms[j].generateTiles(3);
                else if (!north && !east && !south && west)
                    Rooms[j].generateTiles(4);
                else if (north && !east && south && !west)
                    Rooms[j].generateTiles(5);
                else if (!north && east && !south && west)
                    Rooms[j].generateTiles(6);
                else if (north && !east && !south && west)
                    Rooms[j].generateTiles(7);
                else if (north && east && !south && !west)
                    Rooms[j].generateTiles(8);
                else if (!north && east && south && !west)
                    Rooms[j].generateTiles(9);
                else if (!north && !east && south && west)
                    Rooms[j].generateTiles(10);
                else if (!north && east && south && west)
                    Rooms[j].generateTiles(11);
                else if (north && !east && south && west)
                    Rooms[j].generateTiles(12);
                else if (north && east && !south && west)
                    Rooms[j].generateTiles(13);
                else if (north && east && south && !west)
                    Rooms[j].generateTiles(14);
                else if (north && east && south && west)
                    Rooms[j].generateTiles(15);
            }
            north = east = south = west = false; //reset booleans
        }
        buildMiniMap();
    }

    //method that generates enemies based on the dungeon in question.
    public abstract Monster [] generateEnemies();

    public void Enter(int direction){
        //1 = n; 2 = e; 3 = s; 4 = w
        Room.currentMap = this;
        currentRoom = connectionRooms[direction-1]; //set current room to a room according to the passed direction.
        Rooms[currentRoom].Enter(direction);
        contentRoot.getChildren().add(Rooms[currentRoom]); //add current room's graphics.
    }

    public Pane getPane(){
        return sceneRoot;
    }

    public Item generateLoot(){
        switch(Rand.nextInt(6)){
            case 0:{
                return new generic1hBlunt(Difficulty);
            }
            case 1:{
                return new generic1hEdged(Difficulty);
            }
            case 2:{
                return new generic2hBlunt(Difficulty);
            }
            case 3:{
                return new generic2hEdged(Difficulty);
            }
            case 4:{
                return new genericGun(Difficulty);
            }
            default:{
                return generateMapSpecificLoot();
            }
        }
    }

    //generate loot specific to the map
    public abstract Item generateMapSpecificLoot();

    //lets us neatly enter a map from the overworld in a location that makes sense without storing data
    public abstract void enterFromOverworld();

    public void buildMiniMap(){
        StackPane minimap = new StackPane();
        minimap.setAlignment(Pos.CENTER);
        minimap.setTranslateX(600);
        minimap.setTranslateY(-260);
        Rectangle background = new Rectangle(xBound * Room.mapObjectDimension + Room.mapObjectDimension,
                yBound * Room.mapObjectDimension + Room.mapObjectDimension);
        background.setFill(Color.GRAY);
        minimap.getChildren().add(background);
        for(int i = 0; i < xBound*yBound; ++i){
            if(Rooms[i] != null)
                minimap.getChildren().add(Rooms[i].getMapObject((i%xBound)-xBound/2f + .5f, (i/yBound)-yBound/2f + .5f));
            else{
                minimap.getChildren().add(Room.getBlankMapObject((i%xBound)-xBound/2f + .5f, (i/yBound)-yBound/2f + .5f));
            }
        }
        contentRoot.getChildren().add(minimap);
    }
}
