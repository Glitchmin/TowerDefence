package Code;

import Code.gui.IPlayerValuesChangeObserver;

import java.util.ArrayList;
import java.util.List;

public class PlayerValues {
    private Integer gold;
    private Integer mana;
    private final List<IPlayerValuesChangeObserver> playerValuesChangeObserverList;

    public PlayerValues() {
        playerValuesChangeObserverList = new ArrayList<>();
        gold = 100;
        mana = 100;
    }

    public void addObserver(IPlayerValuesChangeObserver observer) {
        playerValuesChangeObserverList.add(observer);
    }

    private void valuesChanged() {
        for (IPlayerValuesChangeObserver observer : playerValuesChangeObserverList) {
            observer.valuesChanged();
        }
    }

    public Integer getGold() {
        return gold;
    }

    public void addGold(Integer gold_increase) {
        gold += gold_increase;
        valuesChanged();
    }

    public void removeGold(Integer gold_decrease) {
        gold -= gold_decrease;
        valuesChanged();
    }

    public Integer getMana() {
        return mana;
    }
}
