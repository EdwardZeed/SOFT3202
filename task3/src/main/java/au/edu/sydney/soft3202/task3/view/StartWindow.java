package au.edu.sydney.soft3202.task3.view;

import au.edu.sydney.soft3202.task3.model.GameBoard;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StartWindow {
    private Scene scene;
    private GameBoard model = new GameBoard();

    private LoginPane loginPane;

    public StartWindow(){

        loginPane = new LoginPane(model);

        this.scene = new Scene(loginPane.getPane(),300,300);



    }

    public Scene getScene(){
        return scene;
    }
}
