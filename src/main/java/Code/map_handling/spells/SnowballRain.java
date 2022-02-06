package Code.map_handling.spells;

import Code.Vector2d;
import Code.map_handling.AbstractSpell;
import Code.map_handling.Meteor;
import Code.map_handling.SpellType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnowballRain extends AbstractSpell {
    private final Meteor meteor;
    private final int numberOfSnowballs;

    public SnowballRain(Vector2d position) {
        cost = 50;
        radius = 1.5;
        numberOfSnowballs = 15;
        meteor = new Meteor(position, 2.0, 1500, 0.4, SpellType.SNOWBALL_RAIN);
        spellType = SpellType.METEOR;
    }

    public List<Meteor> getMeteorList(Vector2d position) {
        Random random = new Random();
        List<Meteor> meteorList = new ArrayList<>();
        for (int i = 0; i < numberOfSnowballs; i++) {
            Vector2d pos = new Vector2d(position.x + (double) random.nextInt((int) (16 * radius)) / 10 - (double) ((int) (8 * radius)) / 10,
                    position.y + (double) random.nextInt((int) (16 * radius)) / 10 - (double) ((int) (8 * radius)) / 10);
            Meteor meteor1 = new Meteor(pos, meteor.getDmg(), meteor.getFreezeTime(),
                    meteor.getRadius(), SpellType.SNOWBALL_RAIN);
            meteor1.setH(3.0 + (double) random.nextInt(120) / 10);
            meteorList.add(meteor1);

        }
        return meteorList;
    }

    @Override
    public String getResourcePath() {
        return "src/main/resources/spells/snowball_rain.png";
    }

    @Override
    public VBox getDescriptionVBox() {
        return new VBox(new Label("damage: " + meteor.getDmg()), new Label("damage radius: " + meteor.getRadius()),
                new Label("stun time: " + (double) meteor.getFreezeTime() / 1000 + " s"), new Label("cost: " + cost));
    }
}
