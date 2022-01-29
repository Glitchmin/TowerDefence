package Code.gui;

import Code.map_handling.Map;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import static java.lang.System.out;


public class MapVisualizer {
    private final GridPane mapGridPane;
    private final Map map;
    private final MapTileBox[][] landscapeBoxes;
    private Label landscapeNameOnCursorLabel;

    public MapVisualizer(Map map, Integer guiElementBoxWidth, Integer guiElementBoxHeight) {
        landscapeNameOnCursorLabel = new Label("");
        MapTileBox.setWidth(guiElementBoxWidth);
        MapTileBox.setHeight(guiElementBoxHeight);
        this.map = map;
        mapGridPane = new GridPane();
        landscapeBoxes = new MapTileBox[map.getWidth()][map.getHeight()];
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                MapTileBox mapTileBox = new MapTileBox(map.getLandscape(i, j));
                mapGridPane.add(mapTileBox.getVBox(), i, j);
                landscapeBoxes[i][j] = mapTileBox;
                mapTileBox.getVBox().setOnMouseEntered(Action -> setLandscapeNameOnCursorLabel(mapTileBox));
            }
        }
    }

    public void setLandscapeNameOnCursorLabel(MapTileBox mapTileBox) {
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (mapTileBox == landscapeBoxes[i][j]) {
                    landscapeNameOnCursorLabel.setText
                            (map.getLandscape(i, j).landscapeType.toString() + " (" + i + "," + j + ")");
                }
            }
        }
    }

    public Label getLandscapeNameOnCursorLabel() {
        return landscapeNameOnCursorLabel;
    }

    public GridPane getMapGridPane() {
        return mapGridPane;
    }

}
