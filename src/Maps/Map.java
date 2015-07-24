package Maps;

import Characters.Monster;
import Profile.Game;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

/**
 * Created by Miles Sanguinetti on 5/20/2015.
 */
public abstract class Map extends Tileset{
    Pane contentRoot = new Pane();
    private int xBound, yBound; //the x and y bounds of the map in question
    private Room [] Rooms; //array of rooms contained within the map
    private Map [] Connections = new Map[4]; //connections
    private int [] connectionRooms = new int[4]; //rooms that lead in from connections
    private int currentRoom;
    private int encounterRate; //rate at which enemies are encountered; expressed as a percentage integer
    private Scene scene;

    public Map(){}

    public Map(String name, int xbound, int ybound, int encounterrate, Map North, Map East, Map South, Map West){
        encounterRate = encounterrate;
        contentRoot.setPrefSize(1280, 800);
        scene = new Scene(contentRoot);
        Name = name;
        xBound = xbound;
        yBound = ybound;
        Rooms = new Room[xBound * yBound]; //allocate rooms
        Connections = new Map[4]; //store connections in array
        Connections[0] = North;
        Connections[1] = East;
        Connections[2] = South;
        Connections[3] = West;

        Build(); //call the abstract build method to begin constructing the map.
        Fill(); //call fill to fill in any blanks left by the build method

        //key released listening code
        scene.setOnKeyReleased(event -> {
            //UP CASE
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
                if(Rooms[currentRoom].Move(1)){
                    if(Rand.nextInt(100) < encounterRate){
                        Game.battle.commenceBattle(Game.Player.getParty(), generateEnemies());
                        Game.mainmenu.getCurrentGame().swapToBattle();
                    }
                }
            }
            //RIGHT CASE
            else if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D){
                if(Rooms[currentRoom].Move(2)){
                    if(Rand.nextInt(100) < encounterRate){
                        Game.battle.commenceBattle(Game.Player.getParty(), generateEnemies());
                        Game.mainmenu.getCurrentGame().swapToBattle();
                    }
                }            }
            //DOWN CASE
            else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                if(Rooms[currentRoom].Move(3)){
                    if(Rand.nextInt(100) < encounterRate){
                        Game.battle.commenceBattle(Game.Player.getParty(), generateEnemies());
                        Game.mainmenu.getCurrentGame().swapToBattle();
                    }
                }            }
            //LEFT CASE
            else if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A){
                if(Rooms[currentRoom].Move(4)){
                    if(Rand.nextInt(100) < encounterRate){
                        Game.battle.commenceBattle(Game.Player.getParty(), generateEnemies());
                        Game.mainmenu.getCurrentGame().swapToBattle();
                    }
                }
            }
            //ENTER CASE
            else if (event.getCode() == KeyCode.ENTER) {
                switch (Rooms[currentRoom].Interact()){
                    case 0:{ //just break; nothing happened
                        break;
                    }
                    case 1:{ //exit to north
                        contentRoot.getChildren().remove(Rooms[currentRoom]); //remove this room's graphics
                        if(currentRoom - yBound < 0){ //exited beyond the scope of the map
                            if(Connections[0] != null){
                                Game.currentMap = Connections[0];
                                Connections[0].Enter(3);
                                Game.mainmenu.getCurrentGame().swapToMap();
                            }
                            else
                                Game.mainmenu.getCurrentGame().swapToOverworld();
                        }
                        else{ //a simple change of rooms within the map
                            currentRoom -= yBound; //decrement current room by y bound
                            contentRoot.getChildren().add(Rooms[currentRoom]); //add the one we're entering's
                            Rooms[currentRoom].Enter(3); //enter the new room from the south
                        }
                        break;
                    }
                    case 2:{ //exit to east
                        contentRoot.getChildren().remove(Rooms[currentRoom]); //remove this room's graphics
                        if((currentRoom + 1)%xBound == 0){ //exited beyond the scope of the map
                            if(Connections[1] != null){
                                Game.currentMap = Connections[1]; //set current map to the connection
                                Connections[1].Enter(4); //enter from the west
                                Game.mainmenu.getCurrentGame().swapToMap(); //load the new map's scene, basically
                            }
                            else
                                Game.mainmenu.getCurrentGame().swapToOverworld(); //we have left the scope of this
                                                                                  //entire series of maps
                        }
                        else{ //a simple change of rooms within the map
                            ++currentRoom; //increment current room by 1
                            contentRoot.getChildren().add(Rooms[currentRoom]); //add the one we're entering's
                            Rooms[currentRoom].Enter(4); //enter the new room from the west
                        }
                        break;
                    }
                    case 3:{ //exit to south
                        contentRoot.getChildren().remove(Rooms[currentRoom]); //remove this room's graphics
                        if(currentRoom + yBound >= xBound * yBound){ //exited beyond the scope of the map
                            if(Connections[2] != null){
                                Game.currentMap = Connections[1]; //set current map to the connection
                                Connections[2].Enter(1); //enter from the north
                                Game.mainmenu.getCurrentGame().swapToMap(); //load the new map's scene, basically
                            }
                            else
                                Game.mainmenu.getCurrentGame().swapToOverworld(); //we have left the scope of this
                            //entire series of maps
                        }
                        else{ //a simple change of rooms within the map
                            currentRoom += yBound; //increment currentroom by yBound
                            contentRoot.getChildren().add(Rooms[currentRoom]); //add the one we're entering's
                            Rooms[currentRoom].Enter(1); //enter the new room from the north
                        }
                        break;
                    }
                    case 4:{//exit to west
                        contentRoot.getChildren().remove(Rooms[currentRoom]); //remove this room's graphics
                        if(currentRoom%xBound == 0){ //exited beyond the scope of the map
                            if(Connections[3] != null){
                                Game.currentMap = Connections[1]; //set current map to the connection
                                Connections[3].Enter(2); //enter from the east
                                Game.mainmenu.getCurrentGame().swapToMap(); //load the new map's scene, basically
                            }
                            else
                                Game.mainmenu.getCurrentGame().swapToOverworld(); //we have left the scope of this
                            //entire series of maps
                        }
                        else{ //a simple change of rooms within the map
                            --currentRoom; //decrement currentroom by 1
                            contentRoot.getChildren().add(Rooms[currentRoom]); //add the one we're entering's
                            Rooms[currentRoom].Enter(2); //enter the new room from the east
                        }
                        break;
                    }
                    case 5:{ //treasure case
                        if(Rooms[currentRoom].getLoot() != null){
                            Game.Player.Insert(Rooms[currentRoom].getLoot()); //add in the item
                            //IMPLEMENT NOTIFICATION LATER
                        }
                        break;
                    }
                    case 6:{ //boss case
                        if(Rooms[currentRoom].getBoss() != null){
                            Game.battle.commenceBattle(Game.Player.getParty(), Rooms[currentRoom].getBoss());
                            Game.mainmenu.getCurrentGame().swapToBattle();
                        }
                        break;
                    }
                }
            }
        });
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
            for(int k = 0; k < yBound*xBound; k+=xBound){ //ensure reach row has at least one room and
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
            for(int n = 0; n < yBound*xBound; n+=xBound){ //now ensure that odd rows connect to both sides.
                if(n/xBound == 1){ //for all odd rows...
                    //ensure that it's connected to the previous row (row 0 isn't an issue b/c of odd numbering)

                    if(n != xBound * (yBound - 1)){ //and the next row, assuming that it exists.

                    }
                }

            }
        }
        else{ //fill the map in terms of vertical columns
            for(int k = 0; k < xBound; ++k){ //ensure each row has at least one room + odd columns have rooms connected

            }
            for(int n = 0; n < xBound; ++n){

            }
        }

    }

    //method that generates enemies based on the dungeon in question.
    public abstract Monster [] generateEnemies();

    public void Enter(int direction){
        //1 = n; 2 = e; 3 = s; 4 = w
        Room.currentMap = this;
        currentRoom = connectionRooms[direction-1]; //set current room to a room according to the passed direction.
        contentRoot.getChildren().add(Rooms[currentRoom]); //add current room's graphics.
    }

    public Scene getScene(){
        return scene;
    }
}
