package OAST;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Jedynie zadanie tej klasy jest to otworzenie okna aplikacji

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Controller controller = new Controller();
        Parent root = FXMLLoader.load(getClass().getResource("oast.fxml"));
        primaryStage.setTitle("Symulator M/M/1");
        primaryStage.setScene(new Scene(root, 600, 300));
        primaryStage.show();
//        controller.simulationController(1000000, -1, 0.5, 10000, 10);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
