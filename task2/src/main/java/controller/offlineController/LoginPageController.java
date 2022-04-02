package controller.offlineController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.DummyAPI;



public class LoginPageController {

    @FXML
    private TextField TFToken, TFUsername;

    private Stage stage;
    DummyAPI model = new DummyAPI();
    FXMLLoader loader = new FXMLLoader();

    public void login(){
        String token = TFToken.getText();
        boolean login = model.logIn(token);
        if (login){
            loader.setLocation(getClass().getResource("/view/offlinePage/InfoPage.fxml"));
            Pane root = null;
            try {
                root = loader.load();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            InfoPageController controller = loader.getController();
            controller.setInfoText(model.getUserInfo());
            controller.setStage(stage);
            stage.setScene(new Scene(root));
        }

    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}
