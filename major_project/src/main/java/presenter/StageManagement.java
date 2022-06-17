package presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Model;

import java.util.HashMap;

/**
 * This class stores the stages, relevant controllers to the fxml files, FXMLLoaders and panes.
 * This exists in order to avoid nullPointerException when toggle and communicate between main window and map window.
 */
public class StageManagement {
    /**
     * The constant stages.
     */
    public static HashMap<String, Stage> stages = new HashMap<>();
    /**
     * The constant views.
     */
    public static HashMap<String, Object> views = new HashMap<>();
    /**
     * The constant loaders.
     */
    public static HashMap<String, FXMLLoader> loaders = new HashMap<>();
    /**
     * The constant panes.
     */
    public static HashMap<String, Pane> panes = new HashMap<>();
    public static Model model;
}
