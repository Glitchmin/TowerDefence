package Code.map_handling;

import Code.Vector2d;
import Code.map_handling.spells.MeteorSpell;

public class Meteor {
    protected final Vector2d position;
    protected Double h;
    protected final double radius;
    protected final double dmg;
    protected final long freezeTime;
    private final Integer ID;
    private static Integer meteorCounter = 0;
    private final SpellType spellType;

    public Meteor(Vector2d position, double dmg, long freezeTime, double radius, SpellType spellType) {
        this.h = 4.0;
        this.dmg = dmg;
        this.freezeTime = freezeTime;
        this.radius = radius;
        this.position = position;
        this.spellType = spellType;
        ID = meteorCounter++;
    }

    public double getRadius() {
        return radius;
    }

    public Vector2d getPosition() {
        return position;
    }

    public Double getH() {
        return h;
    }

    public void setH(Double h) {
        this.h = h;
    }

    public double getDmg() {
        return dmg;
    }

    public long getFreezeTime() {
        return freezeTime;
    }

    public Integer getID() {
        return ID;
    }

    public String getResourcePath() {
        return spellType.getResourcePath();
    }
}
