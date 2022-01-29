package Code.gui;

import Code.map_handling.AbstractTurret;
import Code.map_handling.turrets.LaserTurret;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TurretShop {
    private final GridPane gridPane;
    public TurretShop(Integer columnWidth){
        gridPane = new GridPane();
        VBox laserTurretVBox = new VBox(getImageView(new LaserTurret(),columnWidth));
        laserTurretVBox.setPrefWidth(columnWidth);
        laserTurretVBox.setPrefHeight(columnWidth);
        gridPane.add(laserTurretVBox,0,0);
        gridPane.setGridLinesVisible(true);
    }

    public ImageView getImageView(AbstractTurret turret, Integer imageWidth){
        ImageView imageView = new ImageView(ImageLoader.loadImage(turret.getResourcePath()));
        imageView.setFitWidth(imageWidth);
        imageView.setFitHeight(imageWidth);
        return imageView;
    }
    public GridPane getGridPane() {
        return gridPane;
    }
}
