package Code.gui;

import Code.map_handling.LandscapeType;
import Code.map_handling.Map;
import Code.map_handling.TurretBuilder;
import Code.map_handling.TurretType;
import Code.map_handling.turrets.LaserTurret;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import static java.lang.System.out;


public class MapVisualizer implements ITurretChangeObserver {
    private final GridPane mapGridPane;
    private final Map map;
    private final MapTileBox[][] landscapeBoxes;
    private final MapTileBox[][] turretBoxes;
    private final TurretShop turretShop;
    private Label landscapeNameOnCursorLabel;
    private final TurretBuilder turretBuilder;
    private VBox tmpTurret;

    public MapVisualizer(Map map, TurretShop turretShop, Integer guiElementBoxWidth, Integer guiElementBoxHeight, TurretBuilder turretBuilder) {
        landscapeNameOnCursorLabel = new Label("");
        MapTileBox.setWidth(guiElementBoxWidth);
        MapTileBox.setHeight(guiElementBoxHeight);
        this.map = map;
        this.turretShop = turretShop;
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
        this.turretBuilder = turretBuilder;
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


    @Override
    public void turretChanged(int x, int y) {
        if (turretBoxes[x][y]!=null) {
            mapGridPane.getChildren().remove(turretBoxes[x][y].getVBox());
        }
        turretBoxes[x][y] = new MapTileBox(map.getTurret(x, y));
        mapGridPane.add(turretBoxes[x][y].getVBox(), x, y);
    }
}
