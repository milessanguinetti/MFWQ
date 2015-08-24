package Maps;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Miles on 8/4/2015.
 */
public class overWorld implements Serializable{
    private StackPane contentRoot = new StackPane();
    private mapIcon currentZone; //the current zone (map or city) that the player is in.

    public overWorld(){
        contentRoot.setAlignment(Pos.CENTER);
        try(InputStream imginput = Files.newInputStream(Paths.get("resources/images/worldmap.jpg"))){
            ImageView worldMap = new ImageView(new Image(imginput));
            worldMap.setFitWidth(864);
            worldMap.setFitHeight(864); //preserve aspect ratio
            contentRoot.getChildren().add(worldMap);
        }

        catch (IOException e){
            System.out.println("Error loading world map.");
        }
    }



    private static class mapIcon extends StackPane{
        private String Name;
        private int [] Coordinates = new int[2];
        private mapIcon [] Connections = new mapIcon[4];
        private transient ImageView icon;

        public mapIcon(String name, int xcord, int ycord){
            Name = name;
            Coordinates[0] = xcord;
            Coordinates[1] = ycord;
            setAlignment(Pos.CENTER);
            try(InputStream imginput = Files.newInputStream(Paths.get("resources/images/blueicon.png"))){
                icon = new ImageView(new Image(imginput));
                icon.setFitWidth(35);
                icon.setFitHeight(23); //preserve aspect ratio
                getChildren().add(icon);
            }

            catch (IOException e){
                System.out.println("Error loading map icons.");
            }
        }

        public void generatePath(mapIcon toPath, int direction){
            Line newPath = new Line(Coordinates[0], Coordinates[1],
                    toPath.getX(), toPath.getY());
            newPath.setStrokeWidth(5);
            newPath.setStroke(Color.BLACK);
            Connections[direction] = toPath;
            getChildren().add(newPath);
        }

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

        public int getX(){
            return Coordinates[0];
        }

        public int getY(){return Coordinates[1];}
    }

    public Pane getPane(){
        return contentRoot;
    }
}
