package Code.gui;

import Code.PlayerValues;
import Code.game_engine.MainLoop;
import Code.map_handling.Map;
import Code.map_handling.SpellCaster;
import Code.map_handling.TurretBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class App extends Application {

    private Pane paneOfEverything;
    private final static int windowWidth = 1400;
    private final static int windowHeight = 800;
    private final static int mapWidth = 1200;
    private final static int mapHeight = 600;

    @Override
    public void init() {
        GridPane gridPaneOfEverything = new GridPane();
        paneOfEverything = new Pane();
        paneOfEverything.getChildren().add(gridPaneOfEverything);
        Map map = new Map(0);
        PlayerValues playerValues = new PlayerValues();

        TurretBuilder turretBuilder = new TurretBuilder(map, playerValues);
        ShopItemsTracker shopItemsTracker = new ShopItemsTracker(turretBuilder);
        Shop shop = new Shop((1400 - 1200) / 2, shopItemsTracker);

        SpellCaster spellCaster = new SpellCaster(map, playerValues);

        MapVisualizer mapVisualizer = new MapVisualizer(map, paneOfEverything, shop, turretBuilder, spellCaster,
                mapWidth / map.getWidth(), mapHeight / map.getHeight(),
                (double) 1200 / map.getWidth());

        gridPaneOfEverything.add(mapVisualizer.getMapGridPane(), 0, 0);
        ValuesVisualizer valuesVisualizer = new ValuesVisualizer(playerValues);
        playerValues.addObserver(valuesVisualizer);

        gridPaneOfEverything.add(valuesVisualizer.getLabelsVBox(), 0, 1);
        valuesVisualizer.valuesChanged();
        gridPaneOfEverything.add(mapVisualizer.getLandscapeNameOnCursorLabel(), 1, 2);
        map.addObserver(mapVisualizer);
        gridPaneOfEverything.add(shop.getVbox(), 1, 0);

        MainLoop mainLoop = new MainLoop(playerValues, mapVisualizer, map);
        Thread mainLoopThread = new Thread(mainLoop);

        Button startButton = new Button("start!");
        gridPaneOfEverything.add(startButton,0,2);
        startButton.setOnAction(Action -> {gridPaneOfEverything.getChildren().remove(startButton);
            mainLoop.start();});
        mainLoopThread.start();

    }


    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(paneOfEverything, windowWidth, windowHeight);
        primaryStage.setScene(scene);
        Platform.runLater(primaryStage::show);
    }
}
