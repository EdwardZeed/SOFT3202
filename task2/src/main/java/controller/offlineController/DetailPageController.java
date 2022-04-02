package controller.offlineController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.DummyAPI;

public class DetailPageController {
    private Stage stage;
    private DummyAPI model = new DummyAPI();

    @FXML
    private Label DetailPageTitle;
    @FXML
    private Text DetailPageTXT;
    @FXML
    private TextField TFLocation;
    @FXML
    private Button btn;

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void listLoans() {
        DetailPageTitle.setText("Available Loans");
        DetailPageTXT.setText(model.listAvailableLoans());
    }

    public void listAvailableShips() {
        DetailPageTitle.setText("Available Ships");
        DetailPageTXT.setText(model.listAvailableShips());
    }

    public String getLocation(){
        return TFLocation.getText();
    }

    public Button getButton(){
        return btn;
    }

    public void viewMarketplace(String location) {
        DetailPageTitle.setText("Marketplace");
        DetailPageTXT.setText(model.listMarketplace(location));
    }

    public void listNearbyLocations() {
        DetailPageTitle.setText("Nearby Locations");
        DetailPageTXT.setText(model.listNearbyLocations());
    }

    public void obtainLoan() {
        DetailPageTitle.setText("Obtain Loan");
        DetailPageTXT.setText(model.obtainLoan());
    }

    public void listActiveLoan() {
        DetailPageTitle.setText("Active Loans");
        DetailPageTXT.setText(model.listActiveLoan());
    }

    public void purchaseShip() {
        DetailPageTitle.setText("Purchase Ship");
        DetailPageTXT.setText(model.purchaseShip());
    }

    public void listMyships() {
        DetailPageTitle.setText("My Ships");
        DetailPageTXT.setText(model.listMyShips());
    }

    public void purchaseFuel() {
        DetailPageTitle.setText("Purchase Fuel");
        DetailPageTXT.setText(model.purchaseFUEL());
    }

    public TextField getTFLocation() {
        return TFLocation;
    }

    public void purchaseGoods() {
        DetailPageTitle.setText("enter goods below");
        TFLocation.setPromptText("enter goods here");
        String goods = TFLocation.getText();
        DetailPageTXT.setText(model.purchaseGoods(goods));
    }

    public void createFlightPlan() {
        DetailPageTitle.setText("enter destination below");
        String destination = TFLocation.getText();
        DetailPageTXT.setText(model.createFlightPlan(destination));
    }

    public void viewCurrentFlightPlan() {
        DetailPageTitle.setText("Current Flight Plan");
        DetailPageTXT.setText(model.viewMyFlightPlan());
    }

    public void sellGoods() {
        DetailPageTitle.setText("enter goods to sell below");
        String goods = TFLocation.getText();
        System.out.println(goods);
        DetailPageTXT.setText(model.sellGoods(goods));
    }

    public void backToInfoPage(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/offlinePage/InfoPage.fxml"));
        try {
            Pane root = loader.load();
            InfoPageController controller = loader.getController();
            controller.setStage(stage);
            controller.setInfoText(model.getUserInfo());
            stage.setScene(new Scene(root));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
