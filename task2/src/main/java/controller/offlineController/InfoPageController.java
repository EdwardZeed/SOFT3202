package controller.offlineController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InfoPageController {
    @FXML
    private Text InfoText;
    @FXML
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13;

    private Stage stage;

    public void setInfoText(String text){
        InfoText.setText(text);
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void goDetailPage(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/offlinePage/DetailPage.fxml"));
        try{
            Pane root = loader.load();
            stage.setScene(new Scene(root));
            DetailPageController controller = loader.getController();
            controller.setStage(stage);
            if (event.getSource() == btn1) {
                controller.listLoans();
            }
            else if (event.getSource() == btn2) {
                controller.listAvailableShips();
            }
            else if (event.getSource() == btn3) {
                String location = controller.getLocation();
                controller.getButton().setOnAction(e -> {
                    controller.viewMarketplace(location);
                });
            }
            else if (event.getSource() == btn4) {
                controller.listNearbyLocations();
            }
            else if (event.getSource() == btn5) {
                controller.obtainLoan();
            }
            else if (event.getSource() == btn6) {
                controller.listActiveLoan();
            }
            else if (event.getSource() == btn7) {
                controller.purchaseShip();
            }
            else if (event.getSource() == btn8) {
                controller.listMyships();
            }
            else if(event.getSource() == btn9){
                controller.purchaseFuel();
            }
            else if (event.getSource() == btn10) {
                controller.purchaseGoods();
            }
            else if (event.getSource() == btn11) {
                controller.createFlightPlan();
            }
            else if (event.getSource() == btn12) {
                controller.viewCurrentFlightPlan();
            }
            else if (event.getSource() == btn13) {
                controller.getButton().setOnAction(e -> {
                    controller.sellGoods();
                        });

            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
