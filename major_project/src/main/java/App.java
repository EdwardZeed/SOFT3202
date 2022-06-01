import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presenter.StageManagement;
import view.MainWindowView;

import java.io.File;

/**
 * The main application.
 */
public class App extends Application {
    private static String state1;
    private static String state2;

    @Override
    public void start(Stage primaryStage) throws Exception {
        File mainFXML = new File("src/main/resources/view/MainWindow.fxml");
        File mapFXML = new File("src/main/resources/view/MapWindow.fxml");

        FXMLLoader loader = new FXMLLoader(mainFXML.toURI().toURL());
        FXMLLoader MapLoader = new FXMLLoader(mapFXML.toURI().toURL());
        Pane mapRoot = MapLoader.load();

        StageManagement.loaders.put("MapWindow", MapLoader);
        StageManagement.panes.put("MapWindow", mapRoot);

        Scene scene = new Scene(loader.load());
        MainWindowView controller = loader.getController();

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

    /**
     * The entry point of application.
     * Records what mode user choose to use.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        state1 = args[0];
        state2 = args[1];
        System.out.println(state1);
        System.out.println(state2);
        launch(args);
    }
}
