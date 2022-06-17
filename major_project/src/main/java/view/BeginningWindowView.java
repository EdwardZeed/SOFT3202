package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presenter.BeginningWindowPresenter;
import presenter.StageManagement;

import java.io.File;
import java.io.IOException;


/**
 * The class defines all the functionalities of the beginning page.
 */
public class BeginningWindowView {
    private BeginningWindowPresenter presenter;
    @FXML
    private TextField usrInput;

    /**
     * Initialize.
     */
    public void initialize(){
        StageManagement.views.put("BeginningWindow", this);
        this.presenter = new BeginningWindowPresenter(this);
    }

    /**
     * Sets status of the currencyscoop API and pastebin API.
     *
     * @param currencyOnline the currency online
     * @param pastebinOnline the pastebin online
     */
    public void setStatus(boolean currencyOnline, boolean pastebinOnline) {
        this.presenter.setStatus(currencyOnline, pastebinOnline);
    }

    /**
     * handler for user enter the threshold.
     */
    public void enterThreshold() {
        String threshold = usrInput.getText();
        this.presenter.setThreshold(threshold);
    }

    /**
     * Display error.
     *
     * @param error the error
     */
    public void displayError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(error);
        alert.showAndWait();
    }

    /**
     * Build main window.
     *
     * @throws IOException the io exception
     */
    public void buildMainWindow() throws IOException {
        File mainFXML = new File("src/main/resources/view/MainWindow.fxml");
        FXMLLoader mainWindowLoader = new FXMLLoader(mainFXML.toURI().toURL());
        Pane root = mainWindowLoader.load();
        Stage stage = (Stage) usrInput.getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
