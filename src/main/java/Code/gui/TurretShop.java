package Code.gui;

import Code.map_handling.AbstractTurret;
import Code.map_handling.TurretType;
import Code.map_handling.turrets.LaserTurret;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TurretShop {
    private final GridPane gridPane;
    private final VBox shopVBox;
    private final Integer imageSize;
    private VBox selected;
    private TurretType[][] turretTypes;

    public TurretShop(Integer columnWidth) {
        gridPane = new GridPane();
        selected = null;
        this.imageSize = columnWidth;
        Label nameLabel = new Label("turret shop:");
        nameLabel.setAlignment(Pos.BOTTOM_CENTER);
        nameLabel.setMinWidth(2*columnWidth);

        addTurret(new LaserTurret(),0,0);

        gridPane.setGridLinesVisible(true);
        ImageView cancelImage = new ImageView(ImageLoader.loadImage("src/main/resources/cancel.png"));
        cancelImage.setFitWidth(imageSize*2);
        cancelImage.setFitHeight(imageSize/2);
        shopVBox = new VBox(nameLabel, gridPane, cancelImage);
        shopVBox.setPrefWidth(columnWidth * 2);
    }

    private void addTurret(AbstractTurret abstractTurret, int x, int y) {
        VBox laserTurretVBox = new VBox(getImageView(abstractTurret));
        laserTurretVBox.setOnMouseClicked(Action -> markSelected(laserTurretVBox));
        gridPane.add(laserTurretVBox, x, y);
    }


    public void markSelected(VBox turretVBox) {
        selected = turretVBox;
        ImageView imageView = new ImageView(ImageLoader.loadImage("src/main/resources/mark.png"));
        imageView.setFitWidth(imageSize);
        imageView.setFitHeight(imageSize);
        gridPane.add(imageView, 0, 0);
    }

    public ImageView getImageView(AbstractTurret turret) {
        ImageView imageView = new ImageView(ImageLoader.loadImage(turret.getResourcePath()));
        imageView.setFitWidth(imageSize);
        imageView.setFitHeight(imageSize);
        return imageView;
    }

    public VBox getVbox() {
        return shopVBox;
    }
}
