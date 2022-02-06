package Code.map_handling;

import Code.Vector2d;
import Code.map_handling.spells.MeteorSpell;
import Code.map_handling.spells.SnowballRain;

public enum SpellType {
    METEOR,
    SNOWBALL_RAIN;
    public AbstractSpell getNewSpell(Vector2d position){
        return switch (this){
            case METEOR -> new MeteorSpell(position);
            case SNOWBALL_RAIN -> new SnowballRain(position);
        };
    }
    public String getSpellName(){
        return switch (this){
            case METEOR -> "Meteor";
            case SNOWBALL_RAIN -> "Snowballs Rain";
        };
    }
    public String getResourcePath(){
        return switch (this){
            case METEOR -> new MeteorSpell(null).getResourcePath();
            case SNOWBALL_RAIN -> new SnowballRain(null).getResourcePath();
        };
    }
}
