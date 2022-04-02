package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ConcretClasses.*;
import model.SpaceTraderOnline;

import java.io.IOException;
import java.util.ArrayList;

public class SearchPageController implements Controller{
    private String token;
    private final SpaceTraderOnline model = new SpaceTraderOnline();
    private User user;

    @FXML
    private Text errMessage;
    @FXML
    private TextField SearchLoan, SearchShips, LocationTextField, ClassTextField, ShipIdTextField, QuantityTextField, GoodTextField, MarketplaceLocationTextField, LocationTypeTextField, shipId2TextField,destinationTextField;

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void goBackToInfoPage(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/onlinePage/InfoPage.fxml"));
        Pane root = null;
        try {
            root = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) SearchLoan.getScene().getWindow();
        InfoPageController controller = loader.getController();
        controller.setToken(token);
        controller.setUser(user);
        controller.setCreditsText(String.valueOf(user.getCredits()));
        controller.setJoinDateText(user.getJoinDate());
        controller.setUsernameText(user.getUsername());
        controller.setShipCountText(String.valueOf(user.getShipCount()));
        controller.setStructureCountText(String.valueOf(user.getStructureCount()));
        stage.setScene(new Scene(root, 800, 800));
    }

    public void searchLoan() throws IOException {
        SearchShips.setPromptText("input type here");
        String type = SearchShips.getText();
        Loan loan = model.obtainLoan(user, type, this);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/onlinePage/DetailPage.fxml"));
        Pane root = loader.load();
        Stage stage = (Stage) SearchLoan.getScene().getWindow();
        DetailPageController controller = loader.getController();

        controller.showLoan(loan);
        controller.setToken(token);
        controller.setUser(user);
        stage.setScene(new Scene(root, 800, 800));

    }

    public void searchShip() throws IOException {
        SearchLoan.setPromptText("input class here");
        String type = SearchLoan.getText();
        ArrayList<Ship> ships = model.getAvailableShips(user, type, this);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/onlinePage/DetailPage.fxml"));
        Pane root = loader.load();
        Stage stage = (Stage) SearchLoan.getScene().getWindow();
        DetailPageController controller = loader.getController();

        controller.setToken(token);
        controller.setUser(user);
        controller.setShips(ships);
        controller.showShips();
        stage.setScene(new Scene(root, 800, 800));

    }

    public void purchaseShip(){
        String type = ClassTextField.getText();
        String location = LocationTextField.getText();
        Ship ship = model.purchaseShip(user, location, type, this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/onlinePage/DetailPage.fxml"));
        Pane root = null;
        try {
            root = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) SearchLoan.getScene().getWindow();
        DetailPageController controller = loader.getController();
        controller.setUser(user);
        controller.showPurchasedShip(ship);

        stage.setScene(new Scene(root, 800, 800));
    }

    public void purchaseGood(){
        String ShipId = ShipIdTextField.getText();
        String Good = GoodTextField.getText();
        String Quantity = QuantityTextField.getText();

        Order order = model.purchaseOrder(user, ShipId, Good, Integer.valueOf(Quantity), this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/onlinePage/DetailPage.fxml"));
        Pane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DetailPageController controller = loader.getController();
        controller.setUser(user);
        controller.showPurchasedOrder(order);

        Stage stage = (Stage) SearchLoan.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 800));

    }

    public void viewMarketplace(){
        String location = MarketplaceLocationTextField.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/onlinePage/DetailPage.fxml"));
        Pane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) SearchLoan.getScene().getWindow();
        DetailPageController controller = loader.getController();
        controller.setUser(user);
        controller.setToken(token);
        controller.setTitle("Marketplace");

        ArrayList<Goods> goods = model.listMarketplace(user, location, this);
        controller.showMarketplace(goods);
        stage.setScene(new Scene(root, 800, 800));
    }

    public void showNearbyLocation(){
        String type = LocationTypeTextField.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/onlinePage/DetailPage.fxml"));
        Pane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) SearchLoan.getScene().getWindow();
        DetailPageController controller = loader.getController();
        controller.setUser(user);
        controller.setToken(token);
        controller.setTitle("Nearby Locations");

        ArrayList<Location> locations = model.listNearbyLocations(user, type, this);
        controller.showNearbyLocations(locations);
        stage.setScene(new Scene(root, 800, 800));
    }

    public void startJourney(){
        String shipId = shipId2TextField.getText();
        String destination = destinationTextField.getText();

        FlightPlan flightPlan = model.submitFlightPlan(user, shipId, destination, this);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/onlinePage/DetailPage.fxml"));
        Pane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) SearchLoan.getScene().getWindow();
        DetailPageController controller = loader.getController();
        controller.setUser(user);
        controller.showFlightPlan(flightPlan);

        stage.setScene(new Scene(root, 800, 800));

    }

    public void sellGoods(){
        String ShipId = ShipIdTextField.getText();
        String Good = GoodTextField.getText();
        String Quantity = QuantityTextField.getText();

        Order order = model.sellGoods(user, ShipId, Good, Integer.valueOf(Quantity), this);
        Pane root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/onlinePage/DetailPage.fxml"));
        try {
            root = loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        DetailPageController controller = loader.getController();
        controller.setUser(user);
        controller.showPurchasedOrder(order);

        Stage stage = (Stage) SearchLoan.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 800));

    }

    @Override
    public void setErrMessage(String errMessage) {
        this.errMessage.setText(errMessage);
    }
}
