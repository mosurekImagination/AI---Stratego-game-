package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent)loader.load();
        Controller controller = (Controller)loader.getController();
        controller.setStratego(new Stratego(20));


        Rectangle r = new Rectangle();
        Image image = new Image("sample/images/circle.png");
        r.setFill(new ImagePattern(image));
        r.setX(0);
        r.setY(0);
        r.setWidth(100);
        r.setHeight(100);

        Pane canvas = new Pane();
        canvas.getChildren().add(r);

        GridPane gp = (GridPane)((HBox)root).getChildren().get(0);
        gp.addRow(1,r);
        gp.addRow(2,new Text("Tekst"));
        gp.getRowConstraints().get(0);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));

        Stratego stratego = new Stratego(10, controller);
        stratego.printBoard();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
