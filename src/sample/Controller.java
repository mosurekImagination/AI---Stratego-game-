package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Controller {

    @FXML
    Text O_PointsDiff;
    @FXML
    GridPane gp;
    @FXML
    Text tThinking; // not working TO DO
    @FXML
    Text O_Points;
    @FXML
    Text X_Points;
    @FXML
    Rectangle accPlayer;
    @FXML
    TextField size;
    @FXML
    Text Title;
    @FXML
    Text tfFirstPlayerTime;
    @FXML
    Text tfSecondPlayerTime;

    //Game settings inputs
    @FXML
    TextField tfMaxTime;
    @FXML
    CheckBox chbxAIP1;
    @FXML
    CheckBox chbxAIP2;

    @FXML
    ChoiceBox chobxFirstPlayerH;
    @FXML
    ChoiceBox chobxSecondPlayerH;

    @FXML
    ChoiceBox chobxFirstPlayerA;
    @FXML
    ChoiceBox chobxSecondPlayerA;

    @FXML
    TextField tfFirstPlayerDepth;
    @FXML
    TextField tfSecondPlayerDepth;

    @FXML
    ChoiceBox chobxFirstPlayerS;
    @FXML
    ChoiceBox chobxSecondPlayerS;

    Stratego game;
    ArrayList<Rectangle> fields;
    private ImagePattern O_IMAGE = new ImagePattern(new Image("sample/images/circle.png"));
    private ImagePattern X_IMAGE = new ImagePattern(new Image("sample/images/X.png"));
    private ImagePattern HIGHLIGHT = new ImagePattern(new Image("sample/images/highlight.png"));

    static final String MAX_DIFF = "MAX DIFFERENCE";
    static final String MAX_POINTS = "MAX POINTS";

    static final String MIN_MAX_SIMPLY = "SIMPLY MIN-MAX";
    static final String MIN_MAX_ALFABETA = "ALFA BETA MIN-MAX";

    static final String ORDER_TAKE_FIRST = "TAKE FIRST";
    static final String ORDER_TAKE_RANDOM = "TAKE RANDOM";

    public void setStratego(Stratego str){
        game = str;
    }

    //ON BOARD FIELD CLICK LISTENER
    public void onClick(int x,int y){
        System.out.println("Kliknięto przycisk: "+x+" "+y);
        if(game.clickField(x,y)){
            updateStats();
            if(game.getCurrentPlayer() == game.FIRST_PLAYER){
                getField(x,y).setFill(O_IMAGE);
            }
            else{
                getField(x,y).setFill(X_IMAGE);
            }
            onChangeAccPlayer();
        }
    }

    //CHANGE ACC PLAYER PICTURE AND THEN IF AI TURN -> MAKE MOVE
    private void onChangeAccPlayer() {
        if(game.getCurrentPlayer() == game.FIRST_PLAYER){
            accPlayer.setFill(X_IMAGE);
        }
        else{
            accPlayer.setFill(O_IMAGE);
        }
        if(!game.isEnd()) {
            makeAImove();
        }
    }

    private void makeAImove() {
        if(game.isAITurn() && !game.isEnd()){
            //lockThinkingStatus(true); //TO-DO NOT WORKING
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Touple move = game.getAImove();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            onClick(move.x, move.y);
                        }
                    });
                }
            }).start();

            //lockThinkingStatus(false);

        }
    }

    //TO-DO // NOT WORKING
    private void lockThinkingStatus(boolean b) {
        tThinking.setVisible(b);
    }

    public void initialiseAIChoiceBoxes(){
        initHChoiceBox(chobxFirstPlayerH);
        initHChoiceBox(chobxSecondPlayerH);
        initAChoiceBox(chobxFirstPlayerA);
        initAChoiceBox(chobxSecondPlayerA);
        initOChoiceBox(chobxFirstPlayerS);
        initOChoiceBox(chobxSecondPlayerS);
    }

    private void initOChoiceBox(ChoiceBox box) {
        ObservableList<String> cursors = FXCollections.observableArrayList(ORDER_TAKE_FIRST, ORDER_TAKE_RANDOM);
        box.setValue(cursors.get(0));
        box.setItems(cursors);
    }

    //HEURESTIC CHOICE BOX
    private void initHChoiceBox(ChoiceBox box) {
        ObservableList<String> cursors = FXCollections.observableArrayList(MAX_DIFF, MAX_POINTS);
        box.setValue(cursors.get(0));
        box.setItems(cursors);
    }

    private void initAChoiceBox(ChoiceBox box) {
        ObservableList<String> cursors = FXCollections.observableArrayList(MIN_MAX_ALFABETA, MIN_MAX_SIMPLY);
        box.setValue(cursors.get(0));
        box.setItems(cursors);
    }

    public void initialiseBoard(){
        if(game.getCurrentPlayer() == game.FIRST_PLAYER){
            accPlayer.setFill(X_IMAGE);
        }
        else{
            accPlayer.setFill(O_IMAGE);
        }
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

      //NOT WORKING YET
/*    public void lightField(int x, int y) {
        Paint fill = getField(x,y).getFill();
        getField(x,y).setFill(HIGHLIGHT);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getField(x,y).setFill(fill);
    }*/

    //get reference to clicked field
    public Rectangle getField(int x, int y){
        int index = y*game.getSize()+x;
        return fields.get(index);
    }

    private void updateStats(){
        int[] results = game.getPoints();
        X_Points.setText(String.valueOf(results[0]));
        O_Points.setText(String.valueOf(results[1]));
        if(game.getWinner()!=0){
            tfFirstPlayerTime.setText(String.valueOf(game.getFirstPlayerTime()) + " ms");
            tfSecondPlayerTime.setText(String.valueOf(game.getSecondPlayerTime() + " ms"));
            O_PointsDiff.setText(String.valueOf(results[0]-results[1]));
        }
        if(game.getWinner() > 0){
            String winner = "Winner: "+(game.getWinner()==1?"X": "O");
            Title.setText(winner);
        }
        if(game.getWinner()==-1){
            Title.setText("Draw");
        }
    }

    //on exit button listener
    @FXML
    private void exit(){
        System.exit(1);
    }

    @FXML
    private void newGame(){
        //CLEAR GAME TABLE
        gp.getChildren().clear();
        game.newGame(Integer.valueOf(size.getText()), chbxAIP1.isSelected(),chbxAIP2.isSelected());
        gp.getColumnConstraints().clear();
        gp.getRowConstraints().clear();
        updateStats();
        initialiseBoard();
        game.setMaxTime(Double.valueOf(tfMaxTime.getText()));
        //TWO AI PLAYERS -> TWO SETS
        if( chbxAIP1.isSelected() && chbxAIP2.isSelected()){
            game.setAiHeurestic(
                    getHeuristic((String) chobxFirstPlayerH.getValue()), getHeuristic((String) chobxSecondPlayerH.getValue()));
            game.setAiAlgorithm(
                    getAlgorithmType((String) chobxFirstPlayerA.getValue()), getAlgorithmType((String) chobxSecondPlayerA.getValue()));
            game.setDepths(tfFirstPlayerDepth.getText() != null ? Integer.valueOf(tfFirstPlayerDepth.getText()) : 0,
                    tfSecondPlayerDepth.getText() != null ? Integer.valueOf(tfSecondPlayerDepth.getText()) : 0);
            game.setOrderTypes(
                    getOrderType((String) chobxFirstPlayerS.getValue()), getOrderType((String) chobxSecondPlayerS.getValue()));
            makeAImove();
        }

        //ONE AI PLAYER -> SET AI PROPERTIES DEPENDS ON AI CHECKBOX INPUT
        else if(chbxAIP2.isSelected()){
            game.setAiHeurestic(getHeuristic((String) chobxSecondPlayerH.getValue()));
            game.setAiAlgorithm(getAlgorithmType((String) chobxSecondPlayerA.getValue()));
            game.setDepth(tfSecondPlayerDepth.getText() != null ? Integer.valueOf(tfSecondPlayerDepth.getText()) : 0);
            game.setOrderType(getOrderType((String) chobxSecondPlayerS.getValue()));
            makeAImove();
        }
        else if(chbxAIP1.isSelected()){
            game.setAiHeurestic(getHeuristic((String) chobxFirstPlayerH.getValue()));
            game.setAiAlgorithm(getAlgorithmType((String) chobxFirstPlayerA.getValue()));
            game.setDepth(tfFirstPlayerDepth.getText() != null ? Integer.valueOf(tfFirstPlayerDepth.getText()) : 0);
            game.setOrderType(getOrderType((String) chobxFirstPlayerS.getValue()));
            makeAImove();
        }
    }

    //get heuristic static int value from input string
    private int getHeuristic(String heuristic) {
        switch (heuristic) {
            case MAX_DIFF:
                return StrategoAI.HEURESTIC_MAX_DIFFERENCE;
            case MAX_POINTS:
                return StrategoAI.HEURESTIC_MAX_POINTS;
        }
        return -1;
    }

    //get algorithm static int value from input string
    private int getAlgorithmType(String algorithm) {
        switch (algorithm) {
            case MIN_MAX_SIMPLY:
                return StrategoAI.TYPE_MIN_MAX_SIMPLY;
            case MIN_MAX_ALFABETA:
                return StrategoAI.TYPE_MIN_MAX_ALFABETA;
        }
        return -1;
    }

    private int getOrderType(String order) {
        switch (order) {
            case ORDER_TAKE_FIRST:
                return StrategoAI.ORDER_TAKE_FIRST;
            case ORDER_TAKE_RANDOM:
                return StrategoAI.ORDER_TAKE_RANDOM;
        }
        return -1;
    }

    //1 CHOICE BOX LISTENER
    public void onAIChecked1(){
        chobxFirstPlayerH.setDisable(!chbxAIP1.isSelected());
        chobxFirstPlayerA.setDisable(!chbxAIP1.isSelected());
        chobxFirstPlayerS.setDisable(!chbxAIP1.isSelected());
        tfFirstPlayerDepth.setDisable(!chbxAIP1.isSelected());
    }

    //2 CHOICE BOX LISTENER
    public void onAIChecked2(){
        chobxSecondPlayerH.setDisable(!chbxAIP2.isSelected());
        chobxSecondPlayerA.setDisable(!chbxAIP2.isSelected());
        chobxSecondPlayerS.setDisable(!chbxAIP2.isSelected());
        tfSecondPlayerDepth.setDisable(!chbxAIP2.isSelected());
    }
}
