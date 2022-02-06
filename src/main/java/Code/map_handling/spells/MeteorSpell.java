package Code.map_handling.spells;

import Code.map_handling.AbstractSpell;
import Code.map_handling.IShopElement;

public class MeteorSpell extends AbstractSpell implements IShopElement {
    public MeteorSpell(){
        manaCost=30;
    }

    @Override
    public String getResourcePath() {
        return "src/main/resources/spells/meteor.png";
    }
}
