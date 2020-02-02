import Model.Inventory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryController extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View_Controller/MainScreen.fxml"));
        primaryStage.setTitle("Inventory");
        primaryStage.setScene(new Scene(root, 1400, 450));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
