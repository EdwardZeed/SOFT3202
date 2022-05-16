package view;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.controlsfx.control.WorldMapView;
import presenter.MainWindowPresenter;
import presenter.StageManagement;

import java.util.Currency;
import java.util.Locale;

public class MapView {
    @FXML
    private WorldMapView map;



    public void ShowCountry() {
        try {
            System.out.println(map.getSelectedCountries());
            System.out.println(Currency.getInstance(new Locale("", String.valueOf(map.getSelectedCountries().get(0)))).getCurrencyCode());

            String currencyCode = Currency.getInstance(new Locale("", String.valueOf(map.getSelectedCountries().get(0)))).getCurrencyCode();
            Locale loc = new Locale("", String.valueOf(map.getSelectedCountries().get(0)));
            String countryName = loc.getDisplayCountry();

            MainWindowView view = (MainWindowView) StageManagement.controllers.get("MainWindow");
            MainWindowPresenter controller = view.getController();
            //        controller.addCurrencyToListView(currencyCode, countryName);
            controller.addCountry(countryName, currencyCode);

            StageManagement.stages.put("MapStage", (Stage) map.getScene().getWindow());
            StageManagement.controllers.put("MapController", this);
        } catch (Exception e) {
            System.out.println("No country selected");
        }


    }


}
