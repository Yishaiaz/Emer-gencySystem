package View;

import Controller.Controller;
import Model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import DataBaseConnection.SqliteDbConnection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Model model = new Model(SqliteDbConnection.getInstance());
        model.createTables();
        Controller controller = new Controller(model);
        model.addObserver(controller);

        primaryStage.setTitle("Emer-gency");

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("/Views/Main.fxml").openStream());
        Scene scene = new Scene(root, 800, 650);


        scene.getStylesheets().clear();
//        scene.getStylesheets().add(getClass().getResource("/mainScreenStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        View view = fxmlLoader.getController();
        view.setController(controller);
        controller.addObserver(view);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
