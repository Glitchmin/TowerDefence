package Code;

public class PlayerValues {
    private Integer gold;
    private Integer mana;

    public PlayerValues() {
        gold = 100;
        mana = 100;
    }

    public Integer getGold() {
        return gold;
    }

    public void addGold(Integer gold_increase) {
        gold += gold_increase;
    }

    public void removeGold(Integer gold_decrease) {
        gold -= gold_decrease;
    }

    public Integer getMana() {
        return mana;
    }
}
