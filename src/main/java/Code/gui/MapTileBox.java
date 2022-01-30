package Code.gui;

import Code.map_handling.AbstractMapObject;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MapTileBox {
    private final VBox vBox;
    private static Integer width;
    private static Integer height;


    public MapTileBox(AbstractMapObject element) {
        ImageView imageView = new ImageView(ImageLoader.loadImage(element.getResourcePath()));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        vBox = new VBox(imageView);
        vBox.setPrefHeight(width);
        vBox.setPrefWidth(height);
        vBox.setAlignment(Pos.CENTER);
    }

    public static void setWidth(Integer width) {
        MapTileBox.width = width;
    }

    public static void setHeight(Integer height) {
        MapTileBox.height = height;
    }

    public VBox getVBox() {
        return vBox;
    }
}
