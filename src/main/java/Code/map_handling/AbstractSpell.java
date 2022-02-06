package Code.map_handling;

public abstract class AbstractSpell implements IShopElement {
    protected int manaCost;
    protected double radius;
    public abstract String getResourcePath();

    public double getRadius() {
        return radius;
    }
}
