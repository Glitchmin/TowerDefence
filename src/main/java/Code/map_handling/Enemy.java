package Code.map_handling;

import Code.Vector2d;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.System.out;

public class Enemy extends AbstractMapObject {
    private final Double speed;
    private final Integer dmg;
    private Double hp;
    private final Integer goldDrop;
    private Vector2d position;
    private final EnemyType enemyType;
    private static Integer counter = 0;
    private final Integer ID;
    private final long startTime;
    private long pathTime;
    private long maxPathTime;
    private long sumFreezeTime;
    private final List<PathTile> path;

    public Enemy(EnemyType enemyType, long startTime, Map map) {
        pathTime = 0;
        maxPathTime = 0;
        sumFreezeTime = 0;
        this.enemyType = enemyType;
        this.startTime = startTime;
        boolean canSwim;
        switch (enemyType) {
            default:
                speed = 2.0;
                hp = 10.0;
                goldDrop = 5;
                dmg = 1;
                canSwim = false;
        }
        if (canSwim) {
            path = map.getPathSwimming();
        } else {
            path = map.getPathNoSwimming();
        }
        this.ID = counter++;
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

    public void setPositionFromPathTime(){
        maxPathTime = Math.max(maxPathTime, pathTime);
        int pathCounter = (int)((maxPathTime*speed)/1000);
        position = path.get(pathCounter).getPos();
        double pathTileProgress = (maxPathTime*speed - ((pathCounter*1000)))/1000;
        Vector2d positionChange = new Vector2d(0.0,0.0);
        switch (path.get(pathCounter).getDir()){
            case LEFT -> positionChange = positionChange.add(new Vector2d(1.0-pathTileProgress,0.5));
            case RIGHT -> positionChange = positionChange.add(new Vector2d(pathTileProgress,0.5));
            case UP -> positionChange = positionChange.add(new Vector2d(0.5,1.0-pathTileProgress));
            case DOWN -> positionChange = positionChange.add(new Vector2d(0.5,pathTileProgress));
        }
        if (pathTileProgress > 0.5 && path.get(pathCounter+1).getDir()!=path.get(pathCounter).getDir()){
            switch (path.get(pathCounter+1).getDir()){
                case LEFT -> positionChange = new Vector2d(1.0-pathTileProgress,0.5);
                case RIGHT -> positionChange =new Vector2d(pathTileProgress,0.5);
                case UP -> positionChange = new Vector2d(0.5,1.0-pathTileProgress);
                case DOWN -> positionChange = new Vector2d(0.5,pathTileProgress);
            }
        }
        position = position.add(positionChange);

    }

    public void move(long timeOfCalc) {
        pathTime = (timeOfCalc - startTime) - sumFreezeTime;
        setPositionFromPathTime();
    }

    public boolean reachedEnd() {
        maxPathTime = Math.max(maxPathTime, pathTime);
        int pathCounter = (int)((maxPathTime*speed)/1000);
        return pathCounter == path.size()-1;
    }

    public void freeze(long ms){
        sumFreezeTime+=ms;
    }

    public Integer getDmg() {
        return dmg;
    }

    @Override
    public String getResourcePath() {
        return enemyType.getResourcePath();
    }

}
