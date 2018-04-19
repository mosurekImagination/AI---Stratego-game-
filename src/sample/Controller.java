package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Controller {

    @FXML
    GridPane gp;
    @FXML
    Text O_Points;
    @FXML
    Text X_Points;
    @FXML
    TextField size;
    @FXML
    Rectangle accPlayer;
    @FXML
    Text Title;

    Stratego game;
    ArrayList<Rectangle> fields;
    ImagePattern O_IMAGE = new ImagePattern(new Image("sample/images/circle.png"));
    ImagePattern X_IMAGE = new ImagePattern(new Image("sample/images/X.png"));
    ImagePattern HIGHLIGHT = new ImagePattern(new Image("sample/images/highlight.png"));

    public void setStratego(Stratego str){
        game = str;
    }

    public void onClick(int x,int y){
        System.out.println("Kliknięto przycisk: "+x+" "+y);
        if(game.clickField(x,y)){
            //lightField(x,y);
            updateStats();
            changeAccPlayer();
            if(game.getCurrentPlayer() == game.FIRSTPTURN){
                getField(x,y).setFill(O_IMAGE);
            }
            else{
                getField(x,y).setFill(X_IMAGE);
            }
        }
    }

    private void changeAccPlayer() {
        if(game.getCurrentPlayer() == game.FIRSTPTURN){
            accPlayer.setFill(X_IMAGE);
        }
        else{
            accPlayer.setFill(O_IMAGE);
        }
    }

    public void initialiseBoard(){
        changeAccPlayer();
        Title.setText("Stratego");
        size.setText(String.valueOf(game.getSize()));
        gp.setGridLinesVisible(true);
        fields = new ArrayList<>();
        int factor = 800/game.getSize();

        for(int i =0; i < game.getSize(); i++){
            gp.addRow(i);
            gp.addColumn(i);
            gp.getRowConstraints().add(new RowConstraints(factor));
            gp.getColumnConstraints().add(new ColumnConstraints(factor));
            for(int j=0; j < game.getSize(); j++){
                final int a = i;
                final int b = j;

                Rectangle r = new Rectangle();
                r.setWidth(factor);
                r.setHeight(factor);
                if((i+j)%2==0) r.setFill(Color.BLUE);
                r.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        onClick(b,a);
                    }
                });
                fields.add(r);
                gp.add(r,i,j);
            }
        }
    }
    public void lightField(int x, int y) {
        Paint fill = getField(x,y).getFill();
        getField(x,y).setFill(HIGHLIGHT);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getField(x,y).setFill(fill);
    }


    public Rectangle getField(int x, int y){
        int index = y*game.getSize()+x;
        return fields.get(index);
    }

    private void updateStats(){
        int[] results = game.getPoints();
        X_Points.setText(String.valueOf(results[0]));
        O_Points.setText(String.valueOf(results[1]));
        if(game.getWinner() > 0){
            String winner = "Winner: "+(game.getWinner()==1?"X": "O");
            Title.setText(winner);
        }
        if(game.getWinner()==-1){
            Title.setText("Draw");
        }
    }

    @FXML
    private void exit(){
        System.exit(1);
    }
    @FXML
    private void newGame(){
        gp.getChildren().clear();
        game.newGame(Integer.valueOf(size.getText()));
        gp.getColumnConstraints().clear();
        gp.getRowConstraints().clear();
        updateStats();
        initialiseBoard();
    }
}
