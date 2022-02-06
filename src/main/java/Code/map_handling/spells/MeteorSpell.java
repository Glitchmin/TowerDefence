package Code.map_handling.spells;

import Code.Vector2d;
import Code.map_handling.AbstractSpell;
import Code.map_handling.IShopElement;
import Code.map_handling.Meteor;
import Code.map_handling.SpellType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MeteorSpell extends AbstractSpell implements IShopElement {
    private final Meteor meteor;

    public MeteorSpell(Vector2d position) {
        cost = 150;
        radius = 1.0;
        meteor = new Meteor(position, 100.0, 500, radius, SpellType.METEOR);
        spellType = SpellType.METEOR;
    }

    public Meteor getMeteor(Vector2d position) {
        return new Meteor(position, meteor.getDmg(), meteor.getFreezeTime(), meteor.getRadius(), SpellType.METEOR);
    }

    @Override
    public String getResourcePath() {
        return "src/main/resources/spells/meteor.png";
    }

    @Override
    public VBox getDescriptionVBox() {
        return new VBox(new Label("damage: " + meteor.getDmg()), new Label("damage radius: " + meteor.getRadius()),
                new Label("stun time: " + (double)meteor.getFreezeTime()/1000+" s"), new Label("cost: " + cost));
    }
}
