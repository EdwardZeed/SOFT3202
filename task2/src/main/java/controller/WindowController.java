package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ConcretClasses.User;
import model.SpaceTraderOnline;

import java.io.IOException;


public class WindowController implements Controller{
    @FXML
    private TextField tokenField;
    @FXML
    private Text LoginErrText;
    @FXML
    private TextField UsernameTextField;
    private User user;

    private SpaceTraderOnline model = new SpaceTraderOnline();

    public User getUser() {
        return user;
    }


    @FXML
    public void goToInfoPage(ActionEvent event) throws IOException {
        String token = tokenField.getText();
        user = model.loginToken(token, this);
        if (user != null) {
            System.out.println("User logged in");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/onlinePage/InfoPage.fxml"));
            Pane root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 800));

            InfoPageController infoPageController = loader.getController();
            infoPageController.setUser(user);
            infoPageController.setToken(token);

            infoPageController.setCreditsText(String.valueOf(user.getCredits()));
            infoPageController.setJoinDateText(user.getJoinDate());
            infoPageController.setUsernameText(user.getUsername());
            infoPageController.setShipCountText(String.valueOf(user.getShipCount()));
            infoPageController.setStructureCountText(String.valueOf(user.getStructureCount()));

        }
        else{
            LoginErrText.setText("Invalid token");
        }
    }

    public void goToLoginPage(ActionEvent event) throws IOException {
        boolean isAvailable = model.isOnline();
        if (isAvailable) {
            Pane root = FXMLLoader.load(getClass().getResource("/view/onlinePage/LoginPage.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 800));
        }
        else {
            System.out.println("Server is offline");
            Pane root = FXMLLoader.load(getClass().getResource("/view/onlinePage/ServerDownPage.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 800));
        }
    }

    public void SignUp(){
        String username = UsernameTextField.getText();
        User user = model.signUp(username, this);

        if (user != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/onlinePage/InfoPage.fxml"));
            Pane root = null;
            try {
                root = loader.load();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            user = model.loginToken(user.getToken(), this);
            InfoPageController infoPageController = loader.getController();
            infoPageController.setUser(user);
            infoPageController.setToken(user.getToken());

            infoPageController.setCreditsText(String.valueOf(user.getCredits()));
            infoPageController.setJoinDateText(user.getJoinDate());
            infoPageController.setUsernameText(user.getUsername());
            infoPageController.setShipCountText(String.valueOf(user.getShipCount()));
            infoPageController.setStructureCountText(String.valueOf(user.getStructureCount()));
            Stage stage = (Stage) UsernameTextField.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 800));
        }
    }


    @Override
    public void setErrMessage(String errMessage) {
        LoginErrText.setText(errMessage);
    }
}


