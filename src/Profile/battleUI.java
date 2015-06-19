package Profile;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


/**
 * Created by Miles Sanguinetti on 5/21/15.
 */
public class battleUI extends Pane{
    static private int Selection = 0;
    static private battleUIButton [] Left;
    static private battleUIButton [] Right;

    public battleUI() {
        setPrefSize(860, 150);
        setTranslateY(450);
        Rectangle Background = new Rectangle(860, 150); //establish a mostly-translucent shaded background
        Background.setFill(Color.DEEPSKYBLUE);
        Background.setOpacity(.3);
        getChildren().add(Background);
        Left = new battleUIButton[4]; //generally selection and user-input driven labels
        Right = new battleUIButton[4]; //generally information-based, descriptive labels
        for(int i = 0; i < 4; ++i){ //allocate all buttons
            Left[i] = new battleUIButton(i);
            Right[i] = new battleUIButton(i);
        }
        textBox lbox = new textBox(Left[0], Left[1], Left[2], Left[3]);
        textBox rbox = new textBox(Right[0], Right[1], Right[2], Right[3]);
        lbox.setTranslateX(5);
        lbox.setTranslateY(0);
        rbox.setTranslateX(435);
        rbox.setTranslateY(0);
        getChildren().addAll(lbox, rbox);
    }

    private static class textBox extends VBox{
        public textBox(battleUIButton... all){
            getChildren().add(generateLine());
            //generate one line at the very top.

            //for all buttons that we will be adding...
            for(battleUIButton one : all){
                getChildren().addAll(one, generateLine());
            } //add the button as well as a spacing line
        }

        private Line generateLine(){
            Line line = new Line();
            line.setEndX(400);
            line.setStroke(Color.WHITE);
            return line;
        }
    }

    //class strictly for creating buttons in the menu.
    private static class battleUIButton extends StackPane {
        private Text buttonText;
        private int buttonVal;
        private Rectangle buttonShape;

        public battleUIButton(int buttonval) {
            buttonVal = buttonval;

            buttonShape = new Rectangle(420, 35);
            buttonShape.setOpacity(.4);

            buttonText = new Text();
            buttonText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 15));

            setAlignment(Pos.CENTER); //center the button's sub-components.
            getChildren().addAll(buttonShape, buttonText); //add the shape and text to the button.

            setPlain();

            setOnMouseEntered(event -> {
                //ENTER EVENT INFO HERE
                //Left[Selection].setPlain();
                //setBold();
                Selection = buttonVal; //change selection to whatever has been entered.
            });

            setOnMouseClicked(event -> {
                //ENTER EVENT INFO HERE
            });

            setOnMouseReleased(event -> {
                //ENTER RELEASED INFO HERE
            });
        }

        public void setPlain(){
            buttonShape.setOpacity(.6);
            buttonText.setFill(Color.WHITE);
        }

        public void setBold(){
            buttonShape.setOpacity(.4);
            buttonText.setFill(Color.BLACK);
        }

        public void setText(String toset){
            buttonText.setText(toset);
        }

        public String getText(){
            return buttonText.getText();
        }
    }

    public void setTextFocus(int i){
        for(int j = 0; j < 4; ++j)
            Left[j].setPlain();
        Left[i].setBold();
    }

    //prints to the left at just one index specified as an argument.
    public void printLeftAtNextAvailable(String S1){
        for(int i = 0; i < 4; ++i) {
            if (Left[i] != null) {
                if (Left[i].getText().equals("")) {
                    Left[i].setText(S1);
                    break;
                }
            }
        }
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
