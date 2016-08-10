package Profile;

import javafx.geometry.Pos;
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
public class battleUI extends StackPane {
    static private int Selection = 0;
    static private battleUIButton [] Left;
    static private battleUILabel [] Right;

    public battleUI() {
        setTranslateY(300);
        setAlignment(Pos.CENTER);
        Rectangle Background = new Rectangle(1280, 200); //establish a mostly-translucent shaded background
        Background.setFill(Color.GRAY);
        Background.setOpacity(.3);
        Left = new battleUIButton[4]; //generally selection and user-input driven buttons
        Right = new battleUILabel[4]; //generally information-based, descriptive labels
        for(int i = 0; i < 4; ++i){ //allocate all buttons
            Left[i] = new battleUIButton(i);
            Right[i] = new battleUILabel();
        }
        textBox lbox = new textBox(Left[0], Left[1], Left[2], Left[3]);
        textBox rbox = new textBox(Right[0], Right[1], Right[2], Right[3]);
        lbox.setTranslateX(-319); //642, 8
        lbox.setTranslateY(0);
        rbox.setTranslateX(319);
        rbox.setTranslateY(0);
        getChildren().addAll(Background, lbox, rbox);
    }

    private static class textBox extends VBox{
        public textBox(battleUIButton... all){
            setAlignment(Pos.CENTER);
            getChildren().add(generateLine());
            //generate one line at the very top.

            //for all buttons that we will be adding...
            for(battleUIButton one : all){
                getChildren().addAll(one, generateLine());
            } //add the button as well as a spacing line
        }

        public textBox(battleUILabel... all){
            setAlignment(Pos.CENTER);
            getChildren().add(generateLine());
            //generate one line at the very top.

            //for all buttons that we will be adding...
            for(battleUILabel one : all){
                getChildren().addAll(one, generateLine());
            } //add the button as well as a spacing line
        }

        private Line generateLine(){
            Line line = new Line();
            line.setEndX(630);
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

            buttonShape = new Rectangle(630, 45);
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
            buttonText.setFill(Color.LIGHTGRAY);
        }

        public void setBold(){
            buttonShape.setOpacity(.8);
            buttonText.setFill(Color.GOLDENROD);
        }

        public void setText(String toset){
            buttonText.setText(toset);
        }

        public String getText(){
            return buttonText.getText();
        }
    }

    //class strictly for creating labels in the menu.
    private static class battleUILabel extends StackPane {
        private Text buttonText;
        private Rectangle buttonShape;

        public battleUILabel() {
            buttonShape = new Rectangle(630, 45);
            buttonShape.setOpacity(.4);

            buttonText = new Text();
            buttonText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 15));

            setAlignment(Pos.CENTER); //center the button's sub-components.
            getChildren().addAll(buttonShape, buttonText); //add the shape and text to the button.

            setPlain();
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
        if(S1.matches(""))
            return; //don't print empty strings.
        for(int i = 0; i < 4; ++i) {
            if (Left[i] != null) {
                Left[i].setPlain(); //ensure that info printed in this method is not highlit, as it
                                    //is not selectable
                if (Left[i].getText().equals("")) {
                    if(i != 0)
                        if(Left[i-1].getText().matches(S1)) //ensure that we aren't printing a repeat.
                            break;
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
