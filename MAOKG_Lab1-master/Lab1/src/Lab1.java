import javafx.scene.paint.Color;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.shape.*;

import static javafx.scene.paint.Color.rgb;

public class Lab1 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Arnautova Lab1");

        Group root = new Group();
        Scene scene = new Scene(root, 300, 250);

        Rectangle house = new Rectangle();
        house.setX(30);
        house.setY(120);
        house.setWidth(250);
        house.setHeight(100);
        root.getChildren().add(house);
        scene.setFill(rgb(0, 0, 128));
        house.setFill(rgb(128, 0, 0));

        Polygon roof = new Polygon();
        roof.getPoints().addAll(new Double[]{
                30.0, 125.0, 30.0, 120.0,
                125.0, 80.0,
                280.0, 120.0, 280.0, 125.0});
        root.getChildren().add(roof);
        roof.setFill(rgb(128, 128, 128));

        Rectangle chimney = new Rectangle();
        chimney.setX(50);
        chimney.setY(83);
        chimney.setWidth(17);
        chimney.setHeight(32);
        root.getChildren().add(chimney);
        chimney.setFill(Color.BURLYWOOD);

        int x = 70;
        int y = 150;
        int a = 30;
        int nx = 45;
        int winCount = 2;
        for (int i = 0; i < winCount; i++) {
            Rectangle window = new Rectangle();
            window.setX(x);
            window.setY(y);
            window.setWidth(a);
            window.setHeight(a);
            root.getChildren().add(window);
            window.setFill(Color.YELLOW);
            x += a + nx;
        }

        Rectangle door = new Rectangle();
        door.setX(x);
        door.setY(y);
        door.setWidth(a);
        door.setHeight(70);
        root.getChildren().add(door);
        door.setFill(Color.BURLYWOOD);

        int radius = 3;
        Circle lock = new Circle();
        lock.setCenterX(x + a - radius - 1);
        lock.setCenterY(y + a);
        lock.setRadius(radius);
        root.getChildren().add(lock);

        x = 45;
        y = 25;
        a = 10;
        nx = 16 * a;
        int ny = a;
        int starsCount = 4;
        for (int i = 1; i <= starsCount; i++) {
            if(i % 3 == 0){
                y += a + ny;
                x = 15;
            }
            Rectangle window = new Rectangle();
            window.setX(x);
            window.setY(y);
            window.setWidth(a);
            window.setHeight(a);
            root.getChildren().add(window);
            window.setFill(Color.YELLOW);
            x += a + nx;
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
