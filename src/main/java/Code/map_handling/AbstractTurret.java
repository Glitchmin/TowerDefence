package Code.map_handling;

import javafx.scene.layout.VBox;

public abstract class AbstractTurret extends AbstractMapObject {
    protected Double range;
    protected Integer cost;
    protected Integer width;
    protected Integer height;
    public abstract String getTurretName();
    public abstract VBox getDescriptionVBox();

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
