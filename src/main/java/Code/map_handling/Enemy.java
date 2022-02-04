package Code.map_handling;

import Code.Vector2d;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.System.out;

public class Enemy extends AbstractMapObject {
    private final Double speed;
    private Double hp;
    private final Integer goldDrop;
    private Vector2d position;
    private final EnemyType enemyType;
    private static Integer counter = 0;
    private final Integer ID;
    private long lastTimeCalc;
    private Long pathChangeMoment;
    private Integer pathCounter;
    private final List<MoveDirection> path;

    public Enemy(EnemyType enemyType, long lastTimeCalc, Map map) {
        pathCounter = 1;
        pathChangeMoment = null;
        position = new Vector2d(0.0, 4.33 + (double) new Random().nextInt(33) / 100);
        this.enemyType = enemyType;
        this.lastTimeCalc = lastTimeCalc;
        boolean canSwim;
        switch (enemyType) {
            default:
                speed = 2.0;
                hp = 10.0;
                goldDrop = 5;
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

    public void move(long timeOfCalc) {
        Vector2d newPosition = position.add(path.get(pathCounter).getVector2d().multiply(
                speed * ((double) (timeOfCalc - lastTimeCalc) / 1000)));
        if (!Objects.equals(newPosition.IntX(), position.IntX()) || !Objects.equals(newPosition.IntY(), position.IntY())) {
            pathChangeMoment = timeOfCalc + abs(new Random().nextLong() % 250) + 50;
        }
        if (pathChangeMoment != null && timeOfCalc > pathChangeMoment) {
            pathCounter++;
            pathChangeMoment = null;
        }
        position = newPosition;
        lastTimeCalc = timeOfCalc;
    }

    @Override
    public String getResourcePath() {
        return enemyType.getResourcePath();
    }
}
