package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ConcretClasses.Loan;
import model.ConcretClasses.Ship;
import model.ConcretClasses.User;
import model.SpaceTraderOnline;

import java.io.IOException;
import java.util.ArrayList;

public class InfoPageController implements Controller{
//    @FXML
//    private Text creditsText, joinDateText, shipCountText, structureCountText, usernameText;
    @FXML
    private Text creditsText, joinDateText, shipCountText, structureCountText, usernameText;
    @FXML
    private Text InfoPageMsg;


    private User user;
    private String token;
    private SpaceTraderOnline model;


//    public InfoPageController(User user) {
//        this.user = user;
//    }
    public void setCreditsText(String content){
        creditsText.setText("credits: "+content);
    }

    public void setJoinDateText(String context) {
        this.joinDateText.setText("joinedAt: "+context);
    }

    public void setShipCountText(String context) {
        this.shipCountText.setText("shipCount: "+context);
    }

    public void setStructureCountText(String context) {
        this.structureCountText.setText("structureCount: "+context);
    }

    public void setUsernameText(String context) {
        this.usernameText.setText("username: "+context);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void showLoans() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/onlinePage/DetailPage.fxml"));
        Pane root = loader.load();

        DetailPageController detailPageController = loader.getController();
        detailPageController.setUser(user);
        detailPageController.setTitle("available loans");
        ArrayList<Loan> loans = model.listLoans(user, this);
        System.out.println(user.getUsername());
        detailPageController.setLoans(loans);
        detailPageController.showLoans();



        Stage stage = (Stage) creditsText.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 800));

    }


    public void goSearchPage1(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/onlinePage/SearchPage1.fxml"));
        Pane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchPageController searchPageController = loader.getController();
        searchPageController.setUser(user);
        searchPageController.setToken(token);

        Stage stage = (Stage) creditsText.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 800));
    }

    public void showMyShips(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/onlinePage/DetailPage.fxml"));
        Pane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DetailPageController detailPageController = loader.getController();
        detailPageController.setUser(user);
        detailPageController.setTitle("my ships");
        ArrayList<Ship> ships = model.listUserShips(user);
        detailPageController.setShips(ships);
        detailPageController.showDetailedShips();

        Stage stage = (Stage) creditsText.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 800));

    }

    public void showFlightPlans(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/onlinePage/DetailPage.fxml"));
        Pane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DetailPageController detailPageController = loader.getController();
        detailPageController.setUser(user);
        detailPageController.setTitle("my flight plans");
        detailPageController.showMyFlightPlans(user.getShips());

        Stage stage = (Stage) creditsText.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 800));

    }


    @Override
    public void setErrMessage(String errMessage) {
        InfoPageMsg.setText(errMessage);
    }
}
