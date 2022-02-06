package Code.map_handling;

public abstract class AbstractSpell implements IShopElement {
    protected int manaCost;
    public abstract String getResourcePath();
}
