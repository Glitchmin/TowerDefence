package Code.gui;

import Code.Vector2d;
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
    private ImageView mark;
    private VBox selected;
    private TurretType selectedTurret;
    private TurretType[][] turretTypes;
    private final TurretTracker turretObserver;

    public TurretShop(Integer columnWidth, TurretTracker turretObserver) {
        gridPane = new GridPane();
        selected = null;
        mark = null;
        this.imageSize = columnWidth;
        this.turretObserver = turretObserver;
        Label nameLabel = new Label("turret shop:");
        nameLabel.setAlignment(Pos.BOTTOM_CENTER);
        nameLabel.setMinWidth(2 * columnWidth);

        addTurret(new LaserTurret(null), 0, 0);

        gridPane.setGridLinesVisible(true);
        ImageView cancelImage = new ImageView(ImageLoader.loadImage("src/main/resources/cancel.png"));
        cancelImage.setFitWidth(imageSize * 2);
        cancelImage.setFitHeight(imageSize / 2);
        cancelImage.setOnMouseClicked(Action -> unMarkSelected());
        shopVBox = new VBox(nameLabel, gridPane, cancelImage,turretObserver.getVBox());
        shopVBox.setPrefWidth(columnWidth * 2);
    }

    private void addTurret(AbstractTurret abstractTurret, int x, int y) {
        VBox laserTurretVBox = new VBox(getImageView(abstractTurret));
        laserTurretVBox.setOnMouseClicked(Action -> markSelected(laserTurretVBox, abstractTurret));
        gridPane.add(laserTurretVBox, x, y);
    }


    public void markSelected(VBox turretVBox, AbstractTurret turret) {
        if (mark == null) {
            selected = turretVBox;
            mark = new ImageView(ImageLoader.loadImage("src/main/resources/mark.png"));
            mark.setFitWidth(imageSize);
            mark.setFitHeight(imageSize);
            gridPane.add(mark, 0, 0);
            if (turret instanceof LaserTurret) {
                selectedTurret = TurretType.LASER;
            }
            turretObserver.updateVBox(selectedTurret.getNewTurret(null));
        }
    }

    public TurretType getSelectedTurret() {
        return selectedTurret;
    }

    public void unMarkSelected() {
        turretObserver.updateVBox(null);
        gridPane.getChildren().remove(mark);
        mark = null;
        selected = null;
        selectedTurret = null;
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
