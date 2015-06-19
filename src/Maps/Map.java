package Maps;

/**
 * Created by Miles Sanguinetti on 5/20/2015.
 */
public abstract class Map extends Tileset{
    private int xBound, yBound; //the x and y bounds of the map in question
    private Room [] Rooms; //array of rooms contained within the map
    private Map [] Connections; //connections
    private int currentRoom;

    public Map(){}

    public Map(String name, int xbound, int ybound, Map... All){
        Name = name;
        xBound = xbound;
        yBound = ybound;
        Rooms = new Room[xBound * yBound]; //allocate rooms
        Connections = new Map[All.length];
        for(int i = 0; i < All.length; ++i){
            Connections[i] = All[i];
        }

        Build(); //call the abstract build method to begin constructing the map.
        Fill(); //call fill to fill in any blanks left by the build method
    }

    //method that procedurally generates the quintessential parts of the map in question
    public abstract void Build();

    //method that procedurally fills holes left by the build method.
    public void Fill(){

    }
}
