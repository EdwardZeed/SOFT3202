import controller.MainWindowController;
import controller.StageManagement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static String state;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
        Scene scene = new Scene(loader.load());
        MainWindowController controller = loader.getController();

        if (state.equals("offline")) {
            controller.setOffline();
        }
        else{
            controller.setOnline();
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        state = args[0];
        System.out.println(state);
        launch(args);
    }
}
