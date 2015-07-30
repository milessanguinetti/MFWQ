package Structures;

import java.io.PrintWriter;
import java.io.Serializable;

/**
 * Created by Miles Sanguinetti on 3/2/15.
 * Structure base class; allows catch-all structure references
 * furthermore, implements multidimensional removal/insertion/retrieval
 * methods granted that these are not necessarily hingent on the data structure
 * in question assuming that you make use of the abstract retrieval method.
 */
abstract public class Structure implements Serializable{
    //display method
    abstract public void Display();

    //displays this data structure and any data structures that
    //exist with in its nodes and so forth.
    abstract public void multidimensionalDisplay(int indent);

    //a common function for writing a data structure out to file.
    //specifically, it prints its data into the printwriter parameter,
    //node by node. the tier specifies the structure's position in the
    //scope of multiple layers of structures.
    abstract public void writeOut(PrintWriter toWrite, int tier);

    //insertion method
    //integer signifies success or failure
    abstract public int Insert(Node nodeToInsert);

    //insertion method; passes removal call down to subsequent layers and
    //calls a similar function with the rest of the keys
    //integer signifies success or failure
    public int Insert(Node toInsert, String firstKey) {
        Node isRetrieved = Retrieve(firstKey);
        //pointer to return value retrieval call with firstKey as parameter
        if(isRetrieved != null) { //if we found a matching node
            if(isRetrieved.returnStructure() != null)
                return isRetrieved.returnStructure().Insert(toInsert);
        }       //we return the retrieved structure's insertion call.
        return 0; //if either check failed, return 0.
        //we can't call a method off of a null node and we
        //can't go down a layer if there isn't another layer.
    }

    //insertion based on multiple strings
    public int Insert(Node toInsert, String firstKey, String secondKey) {
        Node isRetrieved = Retrieve(firstKey);
        //pointer to return value retrieval call with firstKey as parameter
        if(isRetrieved != null) { //if we found a matching node
            if(isRetrieved.returnStructure() != null)
                return isRetrieved.returnStructure().Insert(toInsert, secondKey);
        }       //we return the retrieved structure's insertion call.
        return 0; //if either check failed, return 0.
        //we can't call a method off of a null node and we
        //can't go down a layer if there isn't another layer.
    }

    //removal method; integer signifies success or failure
    abstract public int Remove(String toRemove);

    //removal method; passes removal call down to subsequent layers and
    //decrements int at 0, it calls the regular removal method
    //integer signifies success or failure
    public int Remove(String firstKey, String secondKey) {
        Node isRetrieved = Retrieve(firstKey);
        //pointer to return value retrieval call with firstKey as parameter
        if(isRetrieved != null) { //if we found a matching node
            if(isRetrieved.returnStructure() != null)
                return isRetrieved.returnStructure().Remove(secondKey);
        }       //we return the retrieved structure's removal call.
        return 0; //if either check failed, return 0.
        //we can't call a method off of a null node and we
        //can't go down a layer if there isn't another layer.
    }

    //multidimensional removal method based on multiple string input.
    public int Remove(String firstKey, String secondKey, String thirdKey) {
        Node isRetrieved = Retrieve(firstKey);
        //pointer to return value retrieval call with firstKey as parameter
        if (isRetrieved != null) { //if we found a matching node
            if (isRetrieved.returnStructure() != null)
                return isRetrieved.returnStructure().Remove(secondKey, thirdKey);
        }       //we return the retrieved structure's removal call.
        return 0; //if either check failed, return 0.
        //we can't call a method off of a null node and we
        //can't go down a layer if there isn't another layer.
    }

    //returns a node based on a passed string
    abstract public Node Retrieve(String toRetrieve);

    //retrieval method; passes retrieval call down to subsequent layers and
    //calls a similar function with the rest of the keys
    //returns null if it cannot be located.
    public Node Retrieve(String firstKey, String secondKey) {
        Node isRetrieved = Retrieve(firstKey);
        //pointer to return value retrieval call with firstKey as parameter
        if (isRetrieved != null) { //if we found a matching node
            if (isRetrieved.returnStructure() != null)
                return isRetrieved.returnStructure().Retrieve(secondKey);
        }       //we return the retrieved structure's retrieve call.
        return null; //if either check failed, return null.
        //we can't call a method off of a null node and we
        //can't go down a layer if there isn't another layer.
    }

    //see above; adds an extra string parameter for an additional layer.
    public Node Retrieve(String firstKey, String secondKey, String thirdKey) {
        Node isRetrieved = Retrieve(firstKey);
        //pointer to return value retrieval call with firstKey as parameter
        if(isRetrieved != null) { //if we found a matching node
            if(isRetrieved.returnStructure() != null)
                return isRetrieved.returnStructure().Retrieve(secondKey, thirdKey);
        }       //we return the retrieved structure's retrieve call.
        return null; //if either check failed, return null.
        //we can't call a method off of a null node and we
        //can't go down a layer if there isn't another layer.
    }
}
