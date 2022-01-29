package Code.gui;

import Code.map_handling.Map;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static java.lang.System.out;

public class App extends Application {

    private GridPane gridPaneOfEverything;
    private Stage primaryStage;
    private final static int windowWidth = 1400;
    private final static int windowHeight = 800;
    private final static int mapWidth = 1200;
    private final static int mapHeight = 600;
    private MapVisualizer mapVisualizer;

    @Override
    public void init() {
        out.println("init");
        gridPaneOfEverything = new GridPane();
        Map map = new Map(0);
        mapVisualizer = new MapVisualizer(map, mapWidth / map.getWidth(), mapHeight / map.getHeight());
        gridPaneOfEverything.add(mapVisualizer.getMapGridPane(), 0, 0);
        gridPaneOfEverything.add(new Label("hmm"), 0, 1);
        gridPaneOfEverything.add(mapVisualizer.getLandscapeNameOnCursorLabel(), 2, 0);

        TurretShop turretShop = new TurretShop((1400 - 1200) / 2);
        gridPaneOfEverything.add(turretShop.getGridPane(), 1, 0);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(gridPaneOfEverything, windowWidth, windowHeight);
        primaryStage.setScene(scene);
        this.primaryStage = primaryStage;
        Platform.runLater(primaryStage::show);
    }
}
