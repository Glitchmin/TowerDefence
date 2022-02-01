package Code.map_handling;

import javafx.scene.layout.VBox;

public abstract class AbstractTurret extends AbstractMapObject {
    protected Double range;
    protected Integer cost;
    protected Integer width;
    protected Integer height;
    protected Integer level = 1;
    protected boolean isOnMap = false;
    public abstract String getTurretName();
    public abstract VBox getDescriptionVBox();
    public abstract void upgrade();

    public boolean isOnMap(){
        return isOnMap;
    }

    public Double getRange() {
        return range;
    }

    public Integer getCost() {
        return cost;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}
