package Maps;

/**
 * Created by Miles Sanguinetti on 5/20/2015.
 */
public abstract class Map {
    private int xBound, yBound; //the x and y bounds of the map in question
    private Room [][] Rooms; //a multidimensional array of rooms.

    //returns whether or not the user can move to a selected coordinate
    public boolean Move(int xcoord, int ycoord){
        if(xcoord > xBound || ycoord > yBound || xcoord < 0 || ycoord < 0)
            return false;
        if(Rooms == null)
            return false;
        if(Rooms[xcoord] == null)
            return false;
        if(Rooms[xcoord][ycoord] == null)
            return false;
        return true;
    }

    //method that procedurally generates the map in question
    public abstract void Build();
}
