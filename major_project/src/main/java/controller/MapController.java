package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.WorldMapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;

public class MapController {
    @FXML
    private WorldMapView map;



    public void ShowCountry() throws IOException {
        System.out.println(map.getSelectedCountries());
        System.out.println(Currency.getInstance(new Locale("", String.valueOf(map.getSelectedCountries().get(0)))).getCurrencyCode());

        String currencyCode = Currency.getInstance(new Locale("", String.valueOf(map.getSelectedCountries().get(0)))).getCurrencyCode();
        Locale loc = new Locale("", String.valueOf(map.getSelectedCountries().get(0)));
        String countryName = loc.getDisplayCountry();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
        Pane root = loader.load();

        MainWindowController controller = (MainWindowController) StageManagement.controllers.get("MainWindow");
//        controller.addCurrencyToListView(currencyCode, countryName);
        controller.addCountry(countryName, currencyCode);

        StageManagement.stages.put("MapStage", (Stage) map.getScene().getWindow());
        StageManagement.controllers.put("MapController", this);


    }
}
