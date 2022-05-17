package presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;

public class StageManagement {
    public static HashMap<String, Stage> stages = new HashMap<>();
    public static HashMap<String, Object> views = new HashMap<>();
    public static HashMap<String, FXMLLoader> loaders = new HashMap<>();
    public static HashMap<String, Pane> panes = new HashMap<>();
}
