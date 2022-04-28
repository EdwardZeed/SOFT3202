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

        if (state1.equals("offline")) {
            controller.setCurrencyOffline();
        }
        if (state2.equals("offline")) {
            controller.setPastebinOffline();
        }
        if(state1.equals("online") ) {
            controller.setCurrencyOnline();
        }
        if(state2.equals("online")) {
            controller.setPastebinOnline();
        }
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
