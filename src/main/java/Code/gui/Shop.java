package Code.gui;

import Code.map_handling.*;
import Code.map_handling.spells.MeteorSpell;
import Code.map_handling.turrets.ElectricTurret;
import Code.map_handling.turrets.LaserTurret;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Shop {
    private final GridPane turretsGridPane;
    private final GridPane spellsGridPane;
    private final VBox shopVBox;
    private final Integer imageSize;
    private ImageView mark;
    private TurretType selectedTurret;
    private SpellType selectedSpell;
    public final TurretTracker turretTracker;

    public Shop(Integer columnWidth, TurretTracker turretTracker) {
        this.imageSize = columnWidth;
        mark = new ImageView(ImageLoader.loadImage("src/main/resources/mark.png"));
        mark.setFitWidth(imageSize);
        mark.setFitHeight(imageSize);

        spellsGridPane = new GridPane();
        Label spellShopNameLabel = new Label("spell shop:");
        spellShopNameLabel.setAlignment(Pos.BOTTOM_CENTER);
        spellShopNameLabel.setMinWidth(2 * columnWidth);
        addSpell(new MeteorSpell(null), 0, 0);

        this.turretTracker = turretTracker;
        turretsGridPane = new GridPane();
        Label turretShopNameLabel = new Label("turret shop:");
        turretShopNameLabel.setAlignment(Pos.BOTTOM_CENTER);
        turretShopNameLabel.setMinWidth(2 * columnWidth);
        addTurret(new LaserTurret(null), 0, 0);
        addTurret(new ElectricTurret(null), 1, 0);
        turretsGridPane.add(turretTracker.getVBox(), 0, 1, 1, 2);
        turretsGridPane.setGridLinesVisible(true);

        ImageView cancelImage = new ImageView(ImageLoader.loadImage("src/main/resources/cancel.png"));
        cancelImage.setFitWidth(imageSize * 2);
        cancelImage.setFitHeight(imageSize / 2);
        cancelImage.setOnMouseClicked(Action -> unMarkSelected());
        shopVBox = new VBox(spellShopNameLabel, spellsGridPane, turretShopNameLabel, turretsGridPane, cancelImage, turretTracker.getVBox());
        shopVBox.setPrefWidth(columnWidth * 2);
    }

    private void addSpell(AbstractSpell abstractSpell, int x, int y) {
        VBox spellVBox = new VBox(getImageView(abstractSpell));
        spellVBox.setOnMouseClicked(Action -> markSelected(abstractSpell, x, y));
        spellsGridPane.add(spellVBox, x, y);
    }

    private void addTurret(AbstractTurret abstractTurret, int x, int y) {
        VBox TurretVBox = new VBox(getImageView(abstractTurret));
        TurretVBox.setOnMouseClicked(Action -> markSelected(abstractTurret, x, y));
        turretsGridPane.add(TurretVBox, x, y);
    }


    public void markSelected(IShopElement shopElement, int x, int y) {
        unMarkSelected();
        if (shopElement instanceof AbstractSpell) {
            spellsGridPane.add(mark, x, y);
            if (shopElement instanceof MeteorSpell) {
                selectedSpell = SpellType.METEOR;
            }
            turretTracker.updateVBox(null);
        }

        if (shopElement instanceof AbstractTurret) {
            turretsGridPane.add(mark, x, y);
            selectedTurret = TurretType.turretTypeFromClass((AbstractTurret) shopElement);
            turretTracker.updateVBox(selectedTurret.getNewTurret(null));
        }
    }

    public TurretType getSelectedTurret() {
        return selectedTurret;
    }

    public SpellType getSelectedSpell() {
        return selectedSpell;
    }

    public void unMarkSelected() {
        turretTracker.updateVBox(null);
        turretsGridPane.getChildren().remove(mark);
        selectedTurret = null;
        selectedSpell = null;
        spellsGridPane.getChildren().remove(mark);
    }

    public ImageView getImageView(IShopElement shopElement) {
        ImageView imageView = new ImageView(ImageLoader.loadImage(shopElement.getResourcePath()));
        imageView.setFitWidth(imageSize);
        imageView.setFitHeight(imageSize);
        return imageView;
    }

    public VBox getVbox() {
        return shopVBox;
    }
}
