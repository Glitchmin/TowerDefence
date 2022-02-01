package Code.gui;

import Code.map_handling.*;
import Code.map_handling.turrets.LaserTurret;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


public class MapVisualizer implements ITurretChangeObserver {
    private final GridPane mapGridPane;
    private final Pane paneOfEverything;
    private final Map map;
    private final MapTileBox[][] landscapeBoxes;
    private final MapTileBox[][] turretBoxes;
    private final TurretShop turretShop;
    private Label landscapeNameOnCursorLabel;
    private final TurretTracker turretObserver;
    private final TurretBuilder turretBuilder;
    private VBox tmpTurret;
    private final TreeMap enemyBoxes;

    public MapVisualizer(Map map, Pane paneOfEverything, TurretShop turretShop, TurretTracker turretObserver, TurretBuilder turretBuilder, Integer guiElementBoxWidth, Integer guiElementBoxHeight) {
        enemyBoxes = new TreeMap<Integer,VBox>();
        this.paneOfEverything = paneOfEverything;
        landscapeNameOnCursorLabel = new Label("");
        MapTileBox.setWidth(guiElementBoxWidth);
        MapTileBox.setHeight(guiElementBoxHeight);
        this.map = map;
        this.turretShop = turretShop;
        this.turretObserver = turretObserver;
        this.turretBuilder = turretBuilder;
        mapGridPane = new GridPane();

        landscapeBoxes = new MapTileBox[map.getWidth()][map.getHeight()];
        turretBoxes = new MapTileBox[map.getWidth()][map.getHeight()];

        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                MapTileBox mapTileBox = new MapTileBox(map.getLandscape(i, j));
                mapGridPane.add(mapTileBox.getVBox(), i, j);
                landscapeBoxes[i][j] = mapTileBox;
                mapTileBox.getVBox().setOnMouseEntered(Action ->
                {
                    handleCursorOnMapTile(mapTileBox);
                });
            }
        }

    }

    private void handleCursorOnMapTile(MapTileBox mapTileBox) {
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (mapTileBox == landscapeBoxes[i][j]) {
                    landscapeNameOnCursorLabel.setText
                            (map.getLandscape(i, j).landscapeType.toString() + " (" + i + "," + j + ")");
                    showTmpTurret(i, j);
                }
            }
        }
    }


    private void showTmpTurret(int i, int j) {
        mapGridPane.getChildren().remove(tmpTurret);
        if (turretShop.getSelectedTurret() == TurretType.LASER) {
            tmpTurret = new MapTileBox(new LaserTurret()).getVBox();
            tmpTurret.setOpacity(0.5);
            tmpTurret.setOnMouseClicked(Action -> {
                if (map.getLandscape(i, j).landscapeType == LandscapeType.GRASS
                        || map.getLandscape(i, j).landscapeType == LandscapeType.HILL) {
                    turretBuilder.build(i, j, new LaserTurret());
                }
            });
            mapGridPane.add(tmpTurret, i, j);
        }
    }

    public Label getLandscapeNameOnCursorLabel() {
        return landscapeNameOnCursorLabel;
    }

    public GridPane getMapGridPane() {
        return mapGridPane;
    }

    public void addEnemy(Enemy enemy){
        enemyBoxes.remove(enemy.getID());
        ImageView enemyImageView = new ImageView(ImageLoader.loadImage(enemy.getEnemyType().getResourcePath()));
        enemyImageView.setFitHeight(20);
        enemyImageView.setFitWidth(20);
        enemyImageView.setX(0);
        enemyImageView.setY(100);
        paneOfEverything.getChildren().add(enemyImageView);

    }


    @Override
    public void turretChanged(int x, int y) {
        if (turretBoxes[x][y] != null) {
            mapGridPane.getChildren().remove(turretBoxes[x][y].getVBox());
        }
        turretBoxes[x][y] = new MapTileBox(map.getTurret(x, y));
        mapGridPane.add(turretBoxes[x][y].getVBox(), x, y);
        turretBoxes[x][y].getVBox().setOnMouseClicked(Action -> turretObserver.updateVBox(map.getTurret(x, y)));
    }
}
