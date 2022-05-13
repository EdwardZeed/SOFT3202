package view;

import controller.MainWindowController;
import controller.StageManagement;
import javafx.fxml.FXML;
import org.controlsfx.control.WorldMapView;

import java.io.IOException;
import java.util.Currency;
import java.util.Locale;

public class MapView {
    @FXML
    private WorldMapView map;


    public void ShowCountry() throws IOException {
        showCurrencyInListView();

    }

    private void showCurrencyInListView() throws IOException {
        System.out.println(map.getSelectedCountries());
        System.out.println(Currency.getInstance(new Locale("", String.valueOf(map.getSelectedCountries().get(0)))).getCurrencyCode());

        String currencyCode = Currency.getInstance(new Locale("", String.valueOf(map.getSelectedCountries().get(0)))).getCurrencyCode();
        Locale loc = new Locale("", String.valueOf(map.getSelectedCountries().get(0)));
        String countryName = loc.getDisplayCountry();


        MainWIndowView view = (MainWIndowView) StageManagement.controllers.get("MainWindow");
        MainWindowController controller = view.getController();

//        controller.addCurrencyToListView(currencyCode, countryName);
        controller.addCountry(countryName, currencyCode);

//        StageManagement.stages.put("MapStage", (Stage) map.getScene().getWindow());
//        StageManagement.controllers.put("MapController", this);
    }
}
