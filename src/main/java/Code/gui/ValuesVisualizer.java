package Code.gui;

import Code.PlayerValues;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class ValuesVisualizer implements IPlayerValuesChangeObserver {
    private final Label goldLabel;
    private final Label healthLabel;
    private final Label waveLabel;
    private final PlayerValues playerValues;

    ValuesVisualizer(PlayerValues playerValues) {
        goldLabel = new Label("");
        healthLabel = new Label("");
        waveLabel = new Label("");
        this.playerValues = playerValues;
    }

    public VBox getLabelsVBox() {
        return new VBox(goldLabel, waveLabel, healthLabel);
    }

    @Override
    public void valuesChanged() {
        goldLabel.setText("gold: " + playerValues.getGold());
        healthLabel.setText("health: " + playerValues.getHealth());
        waveLabel.setText("wave: " + playerValues.getWaveNumber());
        if (playerValues.getWaveNumber() == null) {
            waveLabel.setText("wave: " + 0);
        }
    }
}
