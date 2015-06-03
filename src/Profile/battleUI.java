package Profile;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/**
 * Created by Miles Sanguinetti on 5/21/15.
 */
public class battleUI extends Pane{
    Text [] Left = new Text[4]; //generally selection and user-input driven labels
    Text [] Right = new Text[4]; //generally information-based, descriptive labels
    Font Bold; //a font for highlighting a given option
    Font Plain; //a font for non-highlighted options

    public battleUI() {
        setPrefSize(860, 150);
        Rectangle Background = new Rectangle(860, 150); //establish a mostly-translucent shaded background
        Background.setFill(Color.BLACK);
        Background.setOpacity(.3);
        getChildren().add(Background);
    }

    public void setTextFocus(int i){
        for(int j = 0; j < 4; ++j)
            Left[j].setFont(Plain);
        Left[i].setFont(Bold);
    }

    //prints to the left at just one index specified as an argument.
    public void printLeft(String S1, int Index){
        if(Left[Index] != null){
            if(!Left[Index].getText().equals(""))
                return; //don't overwrite extant text.
        }
        Left[Index].setText(S1);
    }

    //Print functions to left labels
    public void printLeft(String S1){
        Left[0].setText(S1);
        Left[1].setText("");
        Left[2].setText("");
        Left[3].setText("");
    }

    public void printLeft(String S1, String S2){
        Left[0].setText(S1);
        Left[1].setText(S2);
        Left[2].setText("");
        Left[3].setText("");
    }

    public void printLeft(String S1, String S2, String S3){
        Left[0].setText(S1);
        Left[1].setText(S2);
        Left[2].setText(S3);
        Left[3].setText("");
    }

    public void printLeft(String S1, String S2, String S3, String S4){
        Left[0].setText(S1);
        Left[1].setText(S2);
        Left[2].setText(S3);
        Left[3].setText(S4);
    }

    //Print functions to right labels
    public void printRight(String S1){
        Right[0].setText(S1);
        Right[1].setText("");
        Right[2].setText("");
        Right[3].setText("");
    }

    public void printRight(String S1, String S2){
        Right[0].setText(S1);
        Right[1].setText(S2);
        Right[2].setText("");
        Right[3].setText("");
    }

    public void printRight(String S1, String S2, String S3){
        Right[0].setText(S1);
        Right[1].setText(S2);
        Right[2].setText(S3);
        Right[3].setText("");
    }

    public void printRight(String S1, String S2, String S3, String S4){
        Right[0].setText(S1);
        Right[1].setText(S2);
        Right[2].setText(S3);
        Right[3].setText(S4);
    }
}
