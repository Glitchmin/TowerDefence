package Code.map_handling.spells;

import Code.Vector2d;
import Code.map_handling.AbstractSpell;
import Code.map_handling.IShopElement;
import Code.map_handling.Meteor;

public class MeteorSpell extends AbstractSpell implements IShopElement {
    private final Meteor meteor;
    public MeteorSpell(Vector2d position){
        manaCost=30;
        radius = 1.0;
        meteor = new Meteor(position,100.0,500,radius);
    }

    public Meteor getMeteor(Vector2d position) {
        return new Meteor(position, meteor.getDmg(), meteor.getFreezeTime(), meteor.getRadius()) ;
    }

    @Override
    public String getResourcePath() {
        return "src/main/resources/spells/meteor.png";
    }
}
