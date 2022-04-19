package au.edu.sydney.soft3202.task3.view;

import au.edu.sydney.soft3202.task3.model.GameBoard;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginPane {
    private GameBoard model;
    private Pane pane;
    private TextField username;
    private Label label;
    private Button loginBtn;
    private GameWindow gameWindow;

    public LoginPane(GameBoard model) {
        this.model = model;
        gameWindow = new GameWindow(model);
        pane = new Pane();
        username = new TextField();
        label = new Label("Enter your name:");

        username.setLayoutX(100);
        username.setLayoutY(100);
        label.setLayoutX(100);
        label.setLayoutY(50);

        loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> {
            model.setUsername(username.getText());
            Scene scene = loginBtn.getScene();
            scene = gameWindow.getScene();
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.setScene(scene);
        });
        pane.getChildren().addAll(label, username, loginBtn);
    }

    public Pane getPane(){
        return pane;
    }
}
