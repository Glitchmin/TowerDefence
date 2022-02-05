package Code.map_handling;

import Code.Vector2d;
import Code.gui.IEnemyChangeObserver;
import javafx.scene.layout.VBox;

import java.util.List;

public abstract class AbstractTurret extends AbstractMapObject implements IEnemyChangeObserver {
    protected Double range;
    protected Integer cost;
    protected Integer width;
    protected Integer height;
    protected Vector2d position;
    protected Integer level = 1;
    protected boolean isOnMap = false;
    public abstract String getTurretName();
    public abstract VBox getDescriptionVBox();
    public abstract void upgrade();
    protected List<Enemy> targets;

    public boolean isInRange(Vector2d pos) {
        return ((pos.x - position.x) * (pos.x - position.x) + (pos.y - position.y) * (pos.y - position.y)) <= range * range;
    }

    public abstract Vector2d fire();

    @Override
    public void enemyChanged(Enemy enemy) {
        if (isInRange(enemy.getPosition()) && !targets.contains(enemy)){
            targets.add(enemy);
        }
        if (!isInRange(enemy.getPosition())){
            targets.remove(enemy);
        }
    }

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

    public Vector2d getPosition() {
        return position;
    }
}
