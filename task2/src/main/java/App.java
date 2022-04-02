import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
    private static String state;
    public static void main(String[] args) {
        state = args[0];
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        StartPage startPage = new StartPage();
////        WindowController controller = new WindowController(view);
////        controller.show();
//        startPage.show();
        if (state.equals("online")) {
            Pane root = FXMLLoader.load(getClass().getResource("/view/onlinePage/StartPage.fxml"));
            Scene scene = new Scene(root, 800, 800);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Space Trader");
            primaryStage.show();
        }
        if (state.equals("offline")) {
            Pane root = FXMLLoader.load(getClass().getResource("/view/offlinePage/StartPage.fxml"));
            Scene scene = new Scene(root, 600, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Space Trader");
            primaryStage.show();
        }


    }

//    public static void main(String[] args) {
//        if (args[0].equals("online")) {
//            System.out.println("online");
//        }
//        if (args[0].equals("offline")) {
//            System.out.println("offline");
//        }
//    }
}
