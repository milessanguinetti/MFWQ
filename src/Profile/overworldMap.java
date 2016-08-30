package Profile;

import Cities.City;
import Cities.Spaghettistan;
import Maps.Map;
import Maps.Valley01;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Miles on 8/4/2015.
 */
public class overworldMap extends StackPane implements Serializable{
    private City [] CityList; //an array containing all cities; held for easy access for methods that need to iterate
                              //over all of them.
    private mapIcon currentZone; //the current zone (map or city) that the player is in.
    private mapIcon [] travelPath = new mapIcon[10];
    private int travelLength = 0;
    private ImageView playerIcon;
    private boolean isAnimating = false;

    public overworldMap(){
        setAlignment(Pos.CENTER);
        try(InputStream imginput = Files.newInputStream(Paths.get("resources/images/worldmap.jpg"))){
            ImageView worldMap = new ImageView(new Image(imginput));
            worldMap.setFitWidth(864);
            worldMap.setFitHeight(864); //preserve aspect ratio
            getChildren().add(worldMap);
        }

        catch (IOException e){
            System.out.println("Error loading world map.");
        }

        currentZone = new dungeonIcon("Valley", -100, -100, "Valley01");
        mapIcon alsoValley = new dungeonIcon("Also Valley", 100, 100, "Valley01");
        getChildren().addAll(currentZone, alsoValley);
        mapIcon totallyNotValley = new dungeonIcon("Totally Not Valley", 200, -100, "Valley01");
        getChildren().add(totallyNotValley);
        cityIcon spagoot = new cityIcon("Spaghettistan", 0, -100, "Spaghettistan");
        getChildren().add(spagoot);
        totallyNotValley.generatePath(spagoot, 3);
        spagoot.generatePath(currentZone, 3);
        spagoot.generatePath(alsoValley, 2);


        if(playerIcon == null){ // load player icon
            try(InputStream imginput = Files.newInputStream(Paths.get("resources/images/player.png"))){
                ImageView playerimage = new ImageView(new Image(imginput));
                playerimage.setFitWidth(32);
                playerimage.setFitHeight(32); //preserve aspect ratio
                playerIcon = playerimage;
                playerIcon.setTranslateX(currentZone.getTranslateX());
                playerIcon.setTranslateY(currentZone.getTranslateY() - 10);
                getChildren().add(playerIcon);
            }

            catch (IOException e){
                System.out.println("Error loading player image.");
            }
        }

        setOnKeyReleased(event -> {
            if(isAnimating)
                return;
            if(event.getCode() == KeyCode.ENTER){
                currentZone.Enter();
            }
            else if(event.getCode() == KeyCode.ESCAPE){
                Game.addOptionsOverlay();
            }
            else if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W){
                currentZone.travelToConnection(0);
            }
            else if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A){
                currentZone.travelToConnection(3);
            }
            else if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S){
                currentZone.travelToConnection(2);
            }
            else if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D){
                currentZone.travelToConnection(1);
            }
        });
    }

    public void generateTravelPath(SequentialTransition toGenerate){
        /*System.out.println("Travel Length: " + travelLength);
        for(int i = 0; i < travelLength; ++i){
            System.out.println(i);
        }*/
        for(int i = 0; i < travelLength; ++i){
            toGenerate.getChildren().add(travelPath[i].generatePathTransition(travelPath[i+1]));
        }
    }

    private abstract class mapIcon extends StackPane {
        private Text nameText;
        private mapIcon[] Connections = new mapIcon[4];
        private transient ImageView icon;
        private int distance = 0;

        public mapIcon(String name, int xcord, int ycord) {
            setTranslateX(xcord);
            setTranslateY(ycord);
            setMaxSize(100, 100);
            setMinSize(100, 100);
            setAlignment(Pos.CENTER);
            try (InputStream imginput = Files.newInputStream(Paths.get("resources/images/blueicon.png"))) {
                icon = new ImageView(new Image(imginput));
                icon.setFitWidth(35);
                icon.setFitHeight(23); //preserve aspect ratio
                getChildren().add(icon);
            } catch (IOException e) {
                System.out.println("Error loading map icons.");
            }

            nameText = new Text(name);
            nameText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 20));
            nameText.setFill(Color.BLACK);
            nameText.setTranslateY(25);
            getChildren().add(nameText);

            setOnMouseReleased(event -> {
                if(isAnimating)
                    return; //ignore input if a character's path is already animating.
                if(currentZone == this) {
                    Enter();
                    return;
                }
                currentZone.calculateDistances(0, this);
                resetAndMap(travelLength+1);
                SequentialTransition journey = new SequentialTransition();
                generateTravelPath(journey);
                journey.play();
                isAnimating = true;
                journey.setOnFinished(event1 -> {
                    currentZone = this;
                    isAnimating = false;
                });
            });
        }

        public abstract void Enter();

        public void travelToConnection(int which){
            if(Connections[which] != null){
                calculateDistances(0, Connections[which]);
                Connections[which].resetAndMap(travelLength+1);
                SequentialTransition journey = new SequentialTransition();
                generateTravelPath(journey);
                journey.play();
                isAnimating = true;
                journey.setOnFinished(event1 -> {
                    currentZone = Connections[which];
                    isAnimating = false;
                });
            }
        }

        public void generatePath(mapIcon toPath, int direction) {
            Line newPath = new Line(getTranslateX(), getTranslateY(),
                    toPath.getTranslateX(), toPath.getTranslateY());
            newPath.setStrokeWidth(5);
            newPath.setStroke(Color.GRAY);
            Connections[direction] = toPath;
            if (direction >= 2) {
                toPath.Connections[direction - 2] = this;
            } else {
                toPath.Connections[direction + 2] = this;
            }
            newPath.setTranslateX((toPath.getTranslateX() - getTranslateX())/2);
            newPath.setTranslateY((toPath.getTranslateY() - getTranslateY())/2);
            getChildren().add(newPath);
            newPath.toBack();
            toPath.toFront(); //ensure that the path rests behind the two icons.
        }

        public PathTransition generatePathTransition(mapIcon toFind){
            int direction = 0;
            for(int i = 0; i < 4; ++i){
                if(Connections[i] == toFind){
                    direction = i;
                    break;
                }
            }
            PathTransition toReturn = new PathTransition();
            toReturn.setCycleCount(1);
            toReturn.setDuration(Duration.millis(1000));
            toReturn.setNode(playerIcon);
            Path path = new Path();
            path.getElements().add(new MoveTo(getTranslateX() + playerIcon.getFitWidth()/2,
                    getTranslateY() + playerIcon.getFitHeight()/2 - 10));
            path.getElements().add(new LineTo(Connections[direction].getTranslateX() + playerIcon.getFitWidth()/2,
                    Connections[direction].getTranslateY() + playerIcon.getFitHeight()/2 - 10));
            toReturn.setPath(path);
            return toReturn;
        }

        public void calculateDistances(int currentDistance, mapIcon toFind){
            //System.out.println("Calculating distance on " + nameText.getText());
            if(distance != 0 && distance < currentDistance)
                return;
            distance = currentDistance + 1;
            //System.out.println("Setting " + nameText.getText() + " to " + distance);
            if(this == toFind) {
                travelLength = distance - 1;
                return;
            }
            for(int i = 0; i < 4; ++i){
                if(Connections[i] != null)
                    Connections[i].calculateDistances(distance, toFind);
            }
        }

        public void resetAndMap(int targetDistance){
            if(distance == 0)
                return;
            //System.out.println(nameText.getText() + " distance is " + distance + "; target distance is " + targetDistance);
            if(distance == targetDistance){
                distance = 0;
                //System.out.println("setting travelpath[" + (targetDistance-1) + "] to " + nameText.getText());
                travelPath[targetDistance-1] = this;
                for(int i = 0; i < 4; ++i){
                    if(Connections[i] != null)
                        Connections[i].resetAndMap(targetDistance-1);

                }
            }
            else{
                distance = 0;
                for(int i = 0; i < 4; ++i){
                    if(Connections[i] != null)
                        Connections[i].resetAndMap(0);
                }
            }
        }



        /*
        public void Redraw(){
            if(icon == null){
                try(InputStream imginput = Files.newInputStream(Paths.get("resources/images/blueicon.png"))){
                    icon = new ImageView(new Image(imginput));
                    icon.setFitWidth(35);
                    icon.setFitHeight(23); //preserve aspect ratio
                    getChildren().add(icon);
                }

                catch (IOException e){
                    System.out.println("Error loading map icons.");
                }
                for(int i = 0; i < 4; ++i){
                    if(Connections[i] != null){
                        Line newPath = new Line(Coordinates[0], Coordinates[1],
                                Connections[i].getX(), Connections[i].getY());
                        newPath.setStrokeWidth(5);
                        newPath.setStroke(Color.BLACK);
                        getChildren().add(newPath);
                        Connections[i].Redraw();
                    }
                }
            }
        }
    }*/
    }

    private class dungeonIcon extends mapIcon{
        private Class thisDungeon;

        public dungeonIcon(String name, int xcoord, int ycoord, String classToInitialize){
            super(name, xcoord, ycoord);
            try {
                thisDungeon = Class.forName("Maps." + classToInitialize);
            }
            catch (Exception e){
                System.out.println(e.getClass());
                System.out.println("Error initializing dungeon icon: " + e.getMessage());
            }
        }

        @Override
        public void Enter(){
            try {
                Constructor constructor = thisDungeon.getConstructor();
                Game.currentMap = (Map)(constructor.newInstance());
                Game.currentMap.enterFromOverworld();
                Game.swapToMap(Game.overworld);
            }
            catch (Exception e){
                System.out.println("Error initializing dungeon: " + e.getMessage());
            }
        }
    }
    private class cityIcon extends mapIcon{
        private Class thisCity;

        public cityIcon(String name, int xcoord, int ycoord, String classToInitialize){
            super(name, xcoord, ycoord);
            try {
                thisCity = Class.forName("Cities." + classToInitialize);
            }
            catch (Exception e){
                System.out.println(e.getClass());
                System.out.println("Error initializing city icon: " + e.getMessage());
            }
        }

        @Override
        public void Enter(){
            try {
                Constructor constructor = thisCity.getConstructor();
                Game.currentCity = (City)(constructor.newInstance());
                Game.swapToCity(Game.overworld);
            }
            catch (Exception e){
                System.out.println("Error initializing city: " + e.getMessage());
            }
        }
    }
}
