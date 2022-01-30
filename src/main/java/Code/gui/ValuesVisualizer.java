package Code.gui;

import Code.PlayerValues;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class ValuesVisualizer implements IPlayerValuesChangeObserver {
    private final Label goldLabel;
    private final Label manaLabel;
    private final Label healthLabel;
    private final PlayerValues playerValues;

    ValuesVisualizer(PlayerValues playerValues) {
        goldLabel = new Label("lol");
        manaLabel = new Label("");
        healthLabel = new Label("");
        this.playerValues = playerValues;
    }

    public VBox getLabelsVBox() {
        return new VBox(goldLabel, manaLabel, healthLabel);
    }

    @Override
    public void valuesChanged() {
        goldLabel.setText("gold: "+playerValues.getGold());
        manaLabel.setText("mana: "+playerValues.getMana());
        healthLabel.setText("health: 0, git gud scrub");
    }
}
