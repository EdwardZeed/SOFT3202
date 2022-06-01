package view;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import presenter.MainWindowPresenter;
import presenter.StageManagement;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Optional;

/**
 * This class defines all the functionalities of building the main window of the application.
 */
public class MainWindowView {
    @FXML
    private ListView<String> listView;
    @FXML
    private TextField amount;
    @FXML
    private Button convertbtn;
    @FXML
    private ComboBox<String> from, to;
    @FXML
    private ImageView play_icon;
    @FXML
    private AnchorPane bg;
    @FXML
    private ProgressIndicator progressIndicator;

    private MediaPlayer mediaPlayer;

    private boolean isLight = true;
    /**
     * The map window pane pre-built from the start of the application.
     */
    Pane map = StageManagement.panes.get("MapWindow");
    private MainWindowPresenter presenter;
    /**
     * The light mode css file.
     */
    File light = new File("src/main/resources/view/light_mode.css");
    /**
     * The dark mode css file.
     */
    File dark = new File("src/main/resources/view/dark_mode.css");

    /**
     * Instantiates a new Main window view.
     */
    public MainWindowView() {
    }

    /**
     * Initialize.
     * Initialize the main window, play theme song and set the progressindicator
     */
    public void initialize() {
        File bgm = new File("src/main/resources/bgm.wav");
        File play_icon_file = new File("src/main/resources/play_icon.png");
        Media media = new Media(bgm.toURI().toString());
        play_icon.setImage(new Image(play_icon_file.toURI().toString()));
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });
        presenter = new MainWindowPresenter(this);
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        progressIndicator.setPrefHeight(50);
        progressIndicator.setPrefWidth(50);
        progressIndicator.setVisible(false);
    }

    /**
     * Gets presenter.
     *
     * @return the presenter
     */
    public MainWindowPresenter getPresenter() {
        return presenter;
    }


    /**
     * Sets status.
     *
     * @param currencyOnline the mode of currencyscoop, true if online, otherwise offline
     * @param pastebinOnline the mode of pastebin, true if online, otherwise offline
     */
    public void setStatus(boolean currencyOnline, boolean pastebinOnline) {

        this.presenter.setStatus(currencyOnline, pastebinOnline);
    }

    /**
     * Event handler for clicking add currency button.
     */
    @FXML
    public void AddCurrency(){
        this.presenter.showMapWindow();
    }

    /**
     * Add currency to listview.
     *
     * @param currencyCode the currency code to add
     * @param countryName  the country name to add
     */
    public void addCurrencyToListView(String currencyCode, String countryName) {

        listView.getItems().add(countryName + "\n" + currencyCode);

    }


    /**
     * Rebuild listview.
     *
     * @param countries the countries
     */
    public void rebuildListView(HashMap<String, String> countries){
        String from = this.from.getValue();
        String to = this.to.getValue();
        this.listView.getItems().clear();
        this.from.getItems().clear();
        this.to.getItems().clear();
        System.out.println("from rebuild"+countries.values());
        countries.forEach((k,v) -> {
            addCurrencyToListView(v,k);
            this.from.getItems().add(v);
            this.to.getItems().add(v);

        });
        this.from.setValue(from);
        this.to.setValue(to);
    }

    /**
     * Event handler for clicking convert button.
     *
     */
    @FXML
    public void handleConvert() {

        String fromCurrency = from.getSelectionModel().getSelectedItem();
        String toCurrency = to.getSelectionModel().getSelectedItem();
        String amount = this.amount.getText();
        this.presenter.getResult(fromCurrency, toCurrency, amount);


    }

    /**
     * Display the conversion result.
     *
     * @param rate   the rate
     * @param result the result
     * @return the result of user choice whether generate pastebin report or not
     */
    public Optional<ButtonType> displayResult(double rate, double result) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Result");

        alert.setHeaderText("Result");
        alert.setContentText("Result: " + result + "\n" + "Rate: " + rate);


        ButtonType yes = new ButtonType("generate report", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("close", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yes, no);
        return alert.showAndWait();

    }

    /**
     * Display generated pastebin URL.
     *
     * @param pastebin the pastebin URL
     */
    public void displayPastebin(String pastebin) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Hyperlink link = new Hyperlink(pastebin);
        link.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URI(pastebin));
            } catch (IOException | URISyntaxException e) {
                displayError(e.getMessage());
            }

        });
        HBox hBox = new HBox();
        Label label = new Label("paste to pastebin");
        hBox.getChildren().add(label);
        hBox.getChildren().add(link);

        alert.getDialogPane().setContent(hBox);
        alert.showAndWait();
    }

    /**
     * Display cache hit alert window.
     *
     * @return the result of user choice whether use cache or request fresh data
     */
    public Optional<ButtonType> displayCacheHit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Currency Converter");
        alert.setContentText("cache hit for this data – use cache, or request fresh data from the API?");
        ButtonType buttonTypeOne = new ButtonType("Use Cache", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeTwo = new ButtonType("Request Fresh Data", ButtonBar.ButtonData.NO);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
//        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
        alert.getDialogPane().getButtonTypes().clear();
        alert.getDialogPane().getButtonTypes().addAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        return alert.showAndWait();
    }


    /**
     * Event handler for user choosing clear listview.
     */
    @FXML
    public void handleClear(){
        this.presenter.clear();
    }

    /**
     * Event handler for removing a currency in listview.
     */
    @FXML
    public void handleRemove() {
        try {
            String selected = listView.getSelectionModel().getSelectedItem();
            String countryName = selected.substring(0, selected.indexOf("\n"));

            this.presenter.remove(countryName);
        } catch (Exception e) {
            this.displayError("Please select a country to remove");
        }
    }

    /**
     * Event handler for clearing cache.
     */
    @FXML
    public void handleClearCache(){
        this.presenter.clearCache();
    }

    /**
     * Event handler for pausing and playing theme song.
     */
    @FXML
    public void handleBGM(){
        this.presenter.controlBGM(mediaPlayer.getStatus());
    }

    /**
     * Event handler for changing light/dark mode.
     */
    @FXML
    public void handleMode(){
        this.presenter.controlMode(isLight);
    }

    /**
     * Display error message.
     *
     * @param error the error
     */
    @FXML
    public void displayError(String error){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(error);
        alert.showAndWait();
    }

    /**
     * Set progress indicator visible/invisible.
     *
     * @param isLoading make progress indicator visible if true, otherwise invisible
     */
    public void setProgressIndicator(boolean isLoading){
        System.out.println(Thread.currentThread().getName());
        progressIndicator.setVisible(isLoading);
    }

    /**
     * Disable convert button.
     */
    public void disableConvert(){
        convertbtn.setDisable(true);
    }

    /**
     * Enable convert button.
     */
    public void enableConvert(){
        convertbtn.setDisable(false);
    }

    /**
     * Pause theme song.
     */
    public void pause(){
        mediaPlayer.pause();
    }

    /**
     * Play theme song.
     */
    public void play(){
        mediaPlayer.play();
    }

    /**
     * Sets dark mode for main window and map window.
     */
    public void setDarkMode() {
        bg.getStylesheets().remove(light.toURI().toString());
        bg.getStylesheets().add(dark.toURI().toString());
        map.getStylesheets().remove(light.toURI().toString());
        map.getStylesheets().add(dark.toURI().toString());
    }

    /**
     * Sets light mode for main window and map window.
     */
    public void setLightMode() {
        bg.getStylesheets().remove(dark.toURI().toString());
        bg.getStylesheets().add(light.toURI().toString());
        map.getStylesheets().remove(dark.toURI().toString());
        map.getStylesheets().add(light.toURI().toString());
    }


    public void setMode(boolean isLight) {
        this.isLight = isLight;
    }
}




