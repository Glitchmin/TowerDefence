package Code.gui;

import Code.map_handling.AbstractTurret;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import static java.lang.System.out;

public class TurretObserver {
    private VBox vBox;

    public TurretObserver() {
        vBox = new VBox(new Label("no turret selected"));
    }

    public void updateVBox(AbstractTurret turret) {
        vBox.getChildren().clear();
        if (turret != null) {
            vBox.getChildren().add(new Label(turret.getTurretName()));
            vBox.getChildren().add(new MapTileBox(turret).getVBox());
            vBox.getChildren().add(turret.getDescriptionVBox());
        } else {
            vBox.getChildren().add(new Label("no turret selected"));
        }

    }

    public VBox getVBox() {
        return vBox;
    }
}
