package Code.map_handling;

import Code.map_handling.spells.MeteorSpell;

public enum SpellType {
    METEOR,
    ICE_BOMB;
    public AbstractSpell getNewSpell(){
        return switch (this){
            case METEOR -> new MeteorSpell();
            case ICE_BOMB -> new MeteorSpell();
        };
    }
}
