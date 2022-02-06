package Code.map_handling;

import Code.Vector2d;

public class Meteor {
    protected final Vector2d position;
    protected Double h;
    protected final double radius;
    protected final double dmg;
    protected final long freezeTime;
    public Meteor(Vector2d position, double dmg, long freezeTime, double radius){
        this.h = 3.0;
        this.dmg = dmg;
        this.freezeTime = freezeTime;
        this.radius = radius;
        this.position = position;
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

    public double getDmg() {
        return dmg;
    }

    public long getFreezeTime() {
        return freezeTime;
    }
}
