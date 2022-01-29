package Code.gui;

import Code.map_handling.Map;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

import static java.lang.System.out;

public class MapVisualizer {
    private final GridPane mapGridPane;
    private final Map map;

    public MapVisualizer(Map map, Integer guiElementBoxWidth, Integer guiElementBoxHeight) {
        GuiElementBox.setWidth(guiElementBoxWidth);
        GuiElementBox.setHeight(guiElementBoxHeight);
        this.map = map;
        mapGridPane = new GridPane();
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                mapGridPane.add(new GuiElementBox(map.getLandscape(i, j)).getVBox(), i, j);
            }
        }
    }

    public GridPane getMapGridPane() {
        return mapGridPane;
    }

}
