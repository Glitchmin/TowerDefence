package Code.map_handling;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public abstract class AbstractSpell implements IShopElement {
    protected int cost;
    protected double radius;

    public abstract String getResourcePath();

    protected SpellType spellType;

    public double getRadius() {
        return radius;
    }

    public SpellType getSpellType() {
        return spellType;
    }

    public int getCost() {
        return cost;
    }

    public abstract VBox getDescriptionVBox();
}
