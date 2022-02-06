package Code.gui;

import Code.map_handling.AbstractSpell;
import Code.map_handling.AbstractTurret;
import Code.map_handling.IShopElement;
import Code.map_handling.TurretBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import static java.lang.System.out;

public class ShopItemsTracker {
    private final VBox vBox;
    private final TurretBuilder turretBuilder;

    public ShopItemsTracker(TurretBuilder turretBuilder) {
        this.turretBuilder = turretBuilder;
        vBox = new VBox(new Label("no turret selected"));
    }

    public void updateVBox(IShopElement shopElement) {
        vBox.getChildren().clear();
        if (shopElement instanceof AbstractTurret turret) {
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
                Button multiUpgradeButton = new Button("upgrade for all money");
                vBox.getChildren().add(multiUpgradeButton);
                multiUpgradeButton.setOnAction(Action -> {
                    turretBuilder.multiUpgrade(turret);
                    updateVBox(turret);
                });
            }
            return;
        }
        if (shopElement instanceof AbstractSpell spell){
            vBox.getChildren().add(new Label(spell.getSpellType().getSpellName()));
            vBox.getChildren().add(spell.getDescriptionVBox());
            return;
        }
        vBox.getChildren().add(new Label("no turret selected"));


    }

    public VBox getVBox() {
        return vBox;
    }
}
