package view;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    Pane map = StageManagement.panes.get("MapWindow");
    private MainWindowPresenter presenter;
    File light = new File("src/main/java/view/light_mode.css");
    File dark = new File("src/main/java/view/dark_mode.css");

    public MainWindowView() {
    }

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

    public MainWindowPresenter getPresenter() {
        return presenter;
    }


    public void setStatus(boolean currencyOnline, boolean pastebinOnline) {

        this.presenter.setStatus(currencyOnline, pastebinOnline);
    }

    public void AddCurrency(){
        System.out.println(this);
        this.presenter.showMapWindow();

    }

    public void addCurrencyToListView(String currencyCode, String countryName) {

        listView.getItems().add(countryName + "\n" + currencyCode);

    }


    public void rebuildListView(HashMap<String, String> countries){
        listView.getItems().clear();
        from.getItems().clear();
        to.getItems().clear();
        System.out.println("from rebuild"+countries.values());
        countries.forEach((k,v) -> {
            addCurrencyToListView(v,k);
            from.getItems().add(v);
            to.getItems().add(v);

        });

    }

    public void handleConvert() {

        try {
            String fromCurrency = from.getSelectionModel().getSelectedItem();
            String toCurrency = to.getSelectionModel().getSelectedItem();
            String amount = this.amount.getText();
            this.presenter.getResult(fromCurrency, toCurrency, amount);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            displayError("no result");
        }


    }

    public void displayResult(double rate, double result, String toOutput) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Result");

        alert.setHeaderText("Result");
        alert.setContentText("Result: " + result + "\n" + "Rate: " + rate + "\n" + "output: " + toOutput +
                "\n" + "click show detail to browse the output website");

        //        make the output link clickable
        Hyperlink link = new Hyperlink(toOutput);
        link.setOnAction(event -> {

            try {
                Desktop.getDesktop().browse(new URI(toOutput));
            } catch (IOException | URISyntaxException e) {
                displayError(e.getMessage());
            }

        });
        alert.getDialogPane().setExpandableContent(link);
        alert.showAndWait();

    }

    public Optional<ButtonType> displayCacheHit(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Currency Converter");
        alert.setContentText("cache hit for this data – use cache, or request fresh data from the API?");
        ButtonType buttonTypeOne = new ButtonType("Use Cache", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeTwo = new ButtonType("Request Fresh Data", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
        return alert.showAndWait();
    }


    public void handleClear(){
        this.presenter.clear();
    }

    public void handleRemove() {
        try {
            String selected = listView.getSelectionModel().getSelectedItem();
            String countryName = selected.substring(0, selected.indexOf("\n"));

            this.presenter.remove(countryName);
        } catch (Exception e) {
            this.displayError("Please select a country to remove");
        }
    }

    public void handleClearCache(){
        this.presenter.clearCache();
    }

    public void handleBGM(){
        this.presenter.controlBGM(mediaPlayer.getStatus());
    }

    public void handleMode(){
        this.presenter.controlMode(isLight);
    }

    public void displayError(String error){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(error);
        alert.showAndWait();
    }

    public void setProgressIndicator(boolean isLoading){
        System.out.println(Thread.currentThread().getName());
        progressIndicator.setVisible(isLoading);
    }

    public void disableConvert(){
        convertbtn.setDisable(true);
    }

    public void enableConvert(){
        convertbtn.setDisable(false);
    }

    public void pause(){
        mediaPlayer.pause();
    }
    public void play(){
        mediaPlayer.play();
    }

    public void setDarkMode() {
        bg.getStylesheets().remove(light.toURI().toString());
        bg.getStylesheets().add(dark.toURI().toString());
        map.getStylesheets().remove(light.toURI().toString());
        map.getStylesheets().add(dark.toURI().toString());
    }

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




