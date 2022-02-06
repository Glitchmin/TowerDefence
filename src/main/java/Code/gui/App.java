package Code.gui;

import Code.PlayerValues;
import Code.game_engine.MainLoop;
import Code.map_handling.Map;
import Code.map_handling.TurretBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    private GridPane gridPaneOfEverything;
    private Pane paneOfEverything;
    private Stage primaryStage;
    private final static int windowWidth = 1400;
    private final static int windowHeight = 800;
    private final static int mapWidth = 1200;
    private final static int mapHeight = 600;
    private MapVisualizer mapVisualizer;
    private PlayerValues playerValues;
    private ValuesVisualizer valuesVisualizer;

    @Override
    public void init() {
        gridPaneOfEverything = new GridPane();
        paneOfEverything = new Pane();
        paneOfEverything.getChildren().add(gridPaneOfEverything);
        Map map = new Map(0);
        playerValues = new PlayerValues();

        TurretBuilder turretBuilder = new TurretBuilder(map, playerValues);
        TurretTracker turretTracker = new TurretTracker(turretBuilder);
        Shop shop = new Shop((1400 - 1200) / 2, turretTracker);

        gridPaneOfEverything.add(shop.getVbox(), 1, 0);

        mapVisualizer = new MapVisualizer(map,paneOfEverything, shop, turretBuilder,
                mapWidth / map.getWidth(), mapHeight / map.getHeight(),
                (double)1200/map.getWidth());

        gridPaneOfEverything.add(mapVisualizer.getMapGridPane(), 0, 0);
        valuesVisualizer = new ValuesVisualizer(playerValues);
        playerValues.addObserver(valuesVisualizer);

        gridPaneOfEverything.add(valuesVisualizer.getLabelsVBox(), 0, 1);
        valuesVisualizer.valuesChanged();
        gridPaneOfEverything.add(mapVisualizer.getLandscapeNameOnCursorLabel(), 2, 0);
        map.addTurretChangeObserver(mapVisualizer);

        MainLoop mainLoop = new MainLoop(playerValues, mapVisualizer, map);
        Thread mainLoopThread = new Thread(mainLoop);
        mainLoopThread.start();

    }


    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(paneOfEverything, windowWidth, windowHeight);
        primaryStage.setScene(scene);
        this.primaryStage = primaryStage;
        Platform.runLater(primaryStage::show);
    }
}
