package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    GridPane gp;

    Stratego game;
    ArrayList<Rectangle> fields;

    public void setStratego(Stratego str){
        game = str;
    }

    public void initialiseBoard(){
        fields = new ArrayList<Rectangle>();

        for(int i =0; i < game.getSize(); i++){
            for(int j=0; j < game.getSize(); j++){
                fields.add(initField())
            }
        }
    }

    public void setField(int x, int y, int player){

    }
    public void lightField(int x, int y){

    }

    public void initialiseBoard(int size){
        Pane canvas = new Pane();
        Rectangle r = new Rectangle();
        r.setFill(Color.WHITE);
        r.setX(0);
        r.setY(0);
        r.setWidth(1500);
        r.setHeight(100);
    }

    public Rectangle getField(int x, int y){
        int index = x*game.getSize()+y;
        return fields.get(index);
    }
}
