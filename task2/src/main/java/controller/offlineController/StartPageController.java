package controller.offlineController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.DummyAPI;

public class StartPageController {
    DummyAPI model = new DummyAPI();


    @FXML
    private Text errMessage;

    public void goToLoginPage() {
        if (model.isAvailable()){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/offlinePage/LoginPage.fxml"));
            Pane root = null;
            try {
                root = loader.load();
                System.out.println(root);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            LoginPageController controller = loader.getController();

            Stage stage = (Stage) errMessage.getScene().getWindow();
            controller.setStage(stage);
            stage.setScene(new Scene(root));
        }
        else{
            errMessage.setText("Server is not available");
        }
    }
}
