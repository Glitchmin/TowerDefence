package Code.gui;

import Code.map_handling.Enemy;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class EnemyBox {
    private final VBox vBox;
    private static Integer width;
    private static Integer height;
    EnemyBox(Enemy enemy){
        ImageView imageView = new ImageView(ImageLoader.loadImage(enemy.getResourcePath()));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        vBox = new VBox(imageView);
        vBox.setPrefHeight(width);
        vBox.setPrefWidth(height);
        vBox.setAlignment(Pos.CENTER);
    }

    public static void setWidth(Integer width) {
        EnemyBox.width = width;
    }

    public static void setHeight(Integer height) {
        EnemyBox.height = height;
    }
}
