package Code;

import Code.gui.IPlayerValuesChangeObserver;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class PlayerValues implements Runnable {
    private Integer gold;
    private Integer health;
    private Integer waveNumber;
    private final List<IPlayerValuesChangeObserver> playerValuesChangeObserverList;

    public PlayerValues() {
        playerValuesChangeObserverList = new ArrayList<>();
        gold = 300;
        health = 5;
    }

    public void addObserver(IPlayerValuesChangeObserver observer) {
        playerValuesChangeObserverList.add(observer);
    }

    private void valuesChanged() {
        for (IPlayerValuesChangeObserver observer : playerValuesChangeObserverList) {
            observer.valuesChanged();
        }
    }

    @Override
    public void run() {
        valuesChanged();
    }

    public Integer getGold() {
        return gold;
    }

    public void addGold(Integer gold_increase) {
        gold += gold_increase;
        Platform.runLater(this);
    }

    public void removeGold(Integer gold_decrease) {
        gold -= gold_decrease;
        Platform.runLater(this);
    }

    public Integer getHealth() {
        return health;
    }

    public void dealDmg(Integer dmg) {
        health -= dmg;
        Platform.runLater(this);
    }

    public Integer getWaveNumber() {
        return waveNumber;
    }

    public void setWaveNumber(Integer waveNumber) {
        this.waveNumber = waveNumber;
        Platform.runLater(this);
    }

    public boolean isPlayerDead() {
        return health <= 0;
    }

}
