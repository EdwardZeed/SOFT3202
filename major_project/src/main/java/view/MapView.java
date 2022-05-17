package view;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.WorldMapView;
import presenter.MainWindowPresenter;
import presenter.StageManagement;

import java.util.Currency;
import java.util.Locale;

public class MapView {
    @FXML
    private WorldMapView map;

    public void initialize(){
        StageManagement.views.put("MapWindow", this);
    }


    public void ShowCountry() {
        try {
            System.out.println(map.getSelectedCountries());
            System.out.println(Currency.getInstance(new Locale("", String.valueOf(map.getSelectedCountries().get(0)))).getCurrencyCode());

            String currencyCode = Currency.getInstance(new Locale("", String.valueOf(map.getSelectedCountries().get(0)))).getCurrencyCode();
            Locale loc = new Locale("", String.valueOf(map.getSelectedCountries().get(0)));
            String countryName = loc.getDisplayCountry();

            MainWindowView view = (MainWindowView) StageManagement.views.get("MainWindow");
            MainWindowPresenter presenter = view.getPresenter();
            //        presenter.addCurrencyToListView(currencyCode, countryName);
            presenter.addCountry(countryName, currencyCode);

            StageManagement.stages.put("MapStage", (Stage) map.getScene().getWindow());
            StageManagement.views.put("MapController", this);
        } catch (Exception e) {
            System.out.println("No country selected");
        }

    }

    public void buildMap(){
        Pane root = StageManagement.panes.get("MapWindow");
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        StageManagement.stages.put("MapStage", stage);
        stage.show();
    }

    public void rebuildMap(){
        Stage stage = StageManagement.stages.get("MapStage");
        stage.show();
    }




}
