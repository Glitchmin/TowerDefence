package Code.gui;

import Code.map_handling.AbstractTurret;
import Code.map_handling.TurretBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import static java.lang.System.out;

public class TurretTracker {
    private VBox vBox;
    private final TurretBuilder turretBuilder;

    public TurretTracker(TurretBuilder turretBuilder) {
        this.turretBuilder = turretBuilder;
        vBox = new VBox(new Label("no turret selected"));
    }

    public void updateVBox(AbstractTurret turret) {
        vBox.getChildren().clear();
        if (turret != null) {
            vBox.getChildren().add(new Label(turret.getTurretName()));
            vBox.getChildren().add(new MapTileBox(turret).getVBox());
            vBox.getChildren().add(turret.getDescriptionVBox());
            if (turret.isOnMap()) {
                Button upgradeButton = new Button("upgrade");
                vBox.getChildren().add(upgradeButton);
                upgradeButton.setOnAction(Action -> {
                    turretBuilder.upgrade(turret);
                    updateVBox(turret);
                });
            }
        } else {
            vBox.getChildren().add(new Label("no turret selected"));
        }

    }

    public VBox getVBox() {
        return vBox;
    }
}
