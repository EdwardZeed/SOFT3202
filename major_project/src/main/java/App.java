import controller.MainWindowController;
import controller.StageManagement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static String state1;
    private static String state2;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
        Scene scene = new Scene(loader.load());
        MainWindowController controller = loader.getController();

        boolean currencyStatus = true;
        boolean pastebinStatus = true;
        if (state1.equals("online")) {
            currencyStatus = true;
        }
        else if (state1.equals("offline")) {
            currencyStatus = false;
        }

        if (state2.equals("online")) {
            pastebinStatus = true;
        }
        else if (state2.equals("offline")) {
            pastebinStatus = false;
        }
        controller.setStatus(currencyStatus, pastebinStatus);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        state1 = args[0];
        state2 = args[1];
        System.out.println(state1);
        System.out.println(state2);
        launch(args);
    }
}
