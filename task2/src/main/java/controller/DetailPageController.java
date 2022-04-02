package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.ConcretClasses.*;
import model.SpaceTraderOnline;

import java.io.IOException;
import java.util.ArrayList;

public class DetailPageController implements Controller {
    private SpaceTraderOnline model = new SpaceTraderOnline();
    private ArrayList<Loan> loans;
    private ArrayList<Ship> ships;
    private String token;
    private User user;

    @FXML
    private ListView DetailPageListView;
    @FXML
    private Label DetailPageTitle;
    @FXML
    private Label DetailPageLabel;
    @FXML
    private TextField DetailPageTextField;


    public void setLoans(ArrayList<Loan> loans) {
        this.loans = loans;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    public void showLoans(){
        for (Loan loan : loans) {
            DetailPageListView.getItems().add(loan.toString());
        }
    }

    public void showShips(){
        for (Ship ship : ships) {
            DetailPageListView.getItems().add(ship.toString());
        }
    }

    public void showLoan(Loan loan){
        if (loan != null) {
            DetailPageListView.getItems().add("due: " + loan.getDue());
            DetailPageListView.getItems().add("id: " + loan.getId());
            DetailPageListView.getItems().add("repaymentAmount: " + String.valueOf(loan.getRepaymentAmount()));
            DetailPageListView.getItems().add("status: " + loan.getStatus());
            DetailPageListView.getItems().add("type: " + loan.getType());
        }else{
            DetailPageListView.getItems().add("Only one loan allowed at a time.");
        }
    }

    public void showPurchasedShip(Ship ship){
        DetailPageListView.getItems().add(ship.purchaseShipString());
    }

    public void showPurchasedOrder(Order order){
        DetailPageListView.getItems().add(order.toString());
    }

    public void setTitle(String text){
        DetailPageTitle.setText(text);
    }

    public void setLabel(String text){
        DetailPageLabel.setText(text);
    }



    public void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/onlinePage/InfoPage.fxml"));
        Pane root = loader.load();

        InfoPageController infoPageController = loader.getController();
        infoPageController.setUser(user);
        infoPageController.setCreditsText(String.valueOf(user.getCredits()));
        infoPageController.setJoinDateText(user.getJoinDate());
        infoPageController.setUsernameText(user.getUsername());
        infoPageController.setShipCountText(String.valueOf(user.getShipCount()));
        infoPageController.setStructureCountText(String.valueOf(user.getStructureCount()));

        Stage stage = (Stage) DetailPageTitle.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 800));
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setToken(String token){
        this.token = token;
    }

    public void showDetailedShips() {
        for(Ship ship : ships) {
            DetailPageListView.getItems().add(ship.purchaseShipString());
        }
    }

    public void showMarketplace(ArrayList<Goods> goods) {
        for(Goods good : goods) {
            DetailPageListView.getItems().add(good.toString());
        }
    }


    public void showNearbyLocations(ArrayList<Location> locations) {
        for(Location location : locations) {
            DetailPageListView.getItems().add(location.DetailedString());
        }
    }

    public void showFlightPlan(FlightPlan flightPlan) {
        DetailPageListView.getItems().add(flightPlan.toString());
    }


    public void showMyFlightPlans(ArrayList<Ship> ships) {
        if (ships == null){
            return;
        }
        for(Ship ship : ships) {
            if (ship.getFlightPlan() != null) {
                DetailPageListView.getItems().add(ship.getFlightPlan().toString());
            }
        }
    }


    @Override
    public void setErrMessage(String errMessage) {
        DetailPageListView.getItems().add(errMessage);
    }
}
