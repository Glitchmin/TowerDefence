package Code.map_handling;

import Code.Vector2d;
import Code.gui.IEnemyChangeObserver;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Enemy extends AbstractMapObject {
    private Double speed;
    private Integer dmg;
    private Double hp;
    private Integer goldDrop;
    private Vector2d position;
    private final EnemyType enemyType;
    private static Integer counter = 0;
    private final Integer ID;
    private final long startTime;
    private long pathTime;
    private long maxPathTime;
    private long sumFreezeTime;
    private final List<PathTile> path;
    private final List<IEnemyChangeObserver> observersList;

    public Enemy(EnemyType enemyType, int level, long startTime, Map map, List<AbstractTurret> observersList) {
        pathTime = 0;
        maxPathTime = 0;
        sumFreezeTime = 0;
        this.observersList = new ArrayList<>(observersList);
        this.enemyType = enemyType;
        this.startTime = startTime;

        if (setParametersFromType(enemyType, level)) {
            path = map.getPathSwimming();
        } else {
            path = map.getPathNoSwimming();
        }
        this.ID = counter++;
    }

    private boolean setParametersFromType(EnemyType enemyType, int level) {
        boolean canSwim = false;
        switch (enemyType) {
            case TANK -> {
                speed = 0.9;
                hp = 50.0+12.0*level;
                goldDrop = 10;
                dmg = 1;
            }
            case RUNNER -> {
                speed = 3.5+0.1*level;
                hp = 10.0+4*level;
                goldDrop = 15;
                dmg = 2;
            }
            case SWIMMER -> {
                speed = 1.5;
                hp = 15.0+7*level;
                goldDrop = 20;
                dmg = 1;
                canSwim = true;
            }
            default -> {
                speed = 1.5+0.1*level;
                hp = 12.0+5.0*level;
                goldDrop = 10;
                dmg = 1;
            }
        }
        return canSwim;
    }

    public Enemy(EnemyType enemyType){
        observersList = null;
        this.enemyType = enemyType;
        ID = -1;
        path = null;
        startTime = -1;
        setParametersFromType(enemyType, 0);
    }

    public boolean isInRange(Vector2d pos, double range) {
        return ((pos.x - position.x) * (pos.x - position.x) + (pos.y - position.y) * (pos.y - position.y)) <= range * range;
    }

    public Integer getID() {
        return ID;
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void addObserver(IEnemyChangeObserver observer) {
        observersList.add(observer);
    }

    public void setPositionFromPathTime() {
        maxPathTime = Math.max(maxPathTime, pathTime);
        int pathCounter = (int) ((maxPathTime * speed) / 1000);
        position = path.get(pathCounter).getPos();
        double pathTileProgress = (maxPathTime * speed - ((pathCounter * 1000))) / 1000;
        Vector2d positionChange = new Vector2d(0.0, 0.0);
        switch (path.get(pathCounter).getDir()) {
            case LEFT -> positionChange = positionChange.add(new Vector2d(1.0 - pathTileProgress, 0.5));
            case RIGHT -> positionChange = positionChange.add(new Vector2d(pathTileProgress, 0.5));
            case UP -> positionChange = positionChange.add(new Vector2d(0.5, 1.0 - pathTileProgress));
            case DOWN -> positionChange = positionChange.add(new Vector2d(0.5, pathTileProgress));
        }
        if (pathTileProgress > 0.5 && path.get(pathCounter + 1).getDir() != path.get(pathCounter).getDir()) {
            switch (path.get(pathCounter + 1).getDir()) {
                case LEFT -> positionChange = new Vector2d(1.0 - pathTileProgress, 0.5);
                case RIGHT -> positionChange = new Vector2d(pathTileProgress, 0.5);
                case UP -> positionChange = new Vector2d(0.5, 1.0 - pathTileProgress);
                case DOWN -> positionChange = new Vector2d(0.5, pathTileProgress);
            }
        }
        positionChanged();
        position = position.add(positionChange);

    }

    public void positionChanged() {
        for (IEnemyChangeObserver observer : observersList) {
            observer.enemyChanged(this);
        }
    }

    public void move(long timeOfCalc) {
        pathTime = (timeOfCalc - startTime) - sumFreezeTime;
        setPositionFromPathTime();
    }

    public boolean reachedEnd() {
        maxPathTime = Math.max(maxPathTime, pathTime);
        int pathCounter = (int) ((maxPathTime * speed) / 1000);
        return pathCounter == path.size() - 1;
    }

    public boolean isDead() {
        return hp < 0;
    }

    public void freeze(long ms) {
        sumFreezeTime += ms;
    }

    public Integer getDmg() {
        return dmg;
    }

    public void decreaseHp(Double dmg) {
        hp -= dmg;
    }

    public Integer getGoldDrop() {
        return goldDrop;
    }

    @Override
    public String getResourcePath() {
        return enemyType.getResourcePath();
    }

}
