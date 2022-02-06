package Code.map_handling;

import Code.Vector2d;
import Code.gui.IEnemyChangeObserver;
import javafx.scene.layout.VBox;

import java.util.List;

import static java.lang.System.out;

public abstract class AbstractTurret extends AbstractMapObject implements IEnemyChangeObserver, IShopElement {
    protected Double range;
    protected Integer cost;
    protected Vector2d position;
    protected Integer level = 1;
    protected boolean isOnMap = false;
    public abstract String getTurretName();
    public abstract VBox getDescriptionVBox();
    public abstract void upgrade();
    protected List<Enemy> targets;


    public abstract List<Vector2d> fire(long currentTime);

    @Override
    public void enemyChanged(Enemy enemy) {
        if (enemy.isInRange(position, range) && !targets.contains(enemy)){
            targets.add(enemy);
        }
        if (!enemy.isInRange(position, range) || enemy.isDead() || enemy.reachedEnd()){
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


    public Vector2d getPosition() {
        return position;
    }
}
