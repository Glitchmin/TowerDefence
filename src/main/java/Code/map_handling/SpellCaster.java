package Code.map_handling;

import Code.PlayerValues;
import Code.Vector2d;
import Code.map_handling.spells.MeteorSpell;
import Code.map_handling.spells.SnowballRain;

public class SpellCaster {
    private final Map map;
    private final PlayerValues playerValues;

    public SpellCaster(Map map, PlayerValues playerValues) {
        this.map = map;
        this.playerValues = playerValues;
    }

    public void castSpell(AbstractSpell spell, Vector2d position) {
        if (playerValues.getGold() >= spell.cost) {
            playerValues.removeGold(spell.cost);
            if (spell instanceof MeteorSpell) {
                map.addMeteor(((MeteorSpell) spell).getMeteor(position));
            }
            if (spell instanceof SnowballRain) {
                for (Meteor meteor : ((SnowballRain) spell).getMeteorList(position)) {
                    map.addMeteor(meteor);
                }
            }
        }
    }
}
