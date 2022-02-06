package Code.map_handling;

import Code.Vector2d;
import Code.map_handling.spells.MeteorSpell;

public enum SpellType {
    METEOR,
    ICE_BOMB;
    public AbstractSpell getNewSpell(Vector2d position){
        return switch (this){
            case METEOR -> new MeteorSpell(position);
            case ICE_BOMB -> new MeteorSpell(position);
        };
    }
    public String getSpellName(){
        return switch (this){
            case METEOR -> "Meteor";
            case ICE_BOMB -> "Ice Bomb";
        };
    }
}
