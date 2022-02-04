package Code.map_handling;

import Code.Vector2d;

import java.util.Random;

import static java.lang.System.out;

public class Enemy extends AbstractMapObject {
    private final Double speed;
    private Double hp;
    private final Integer goldDrop;
    private final Vector2d position;
    private final EnemyType enemyType;
    private static Integer counter = 0;
    private final Integer ID;
    private MoveDirection moveDirection;
    private long lastTimeCalc;

    public Enemy(EnemyType enemyType, long lastTimeCalc) {
        moveDirection = MoveDirection.RIGHT;
        position = new Vector2d(0.0,4.33+(double) new Random().nextInt(33)/100);
        this.enemyType = enemyType;
        this.lastTimeCalc = lastTimeCalc;
        switch (enemyType) {
            default:
                speed = 1.0;
                hp = 10.0;
                goldDrop = 5;
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

    public void move(long timeOfCalc){
        position.x += speed * ((double)(timeOfCalc-lastTimeCalc)/1000);
        lastTimeCalc = timeOfCalc;
    }

    @Override
    public String getResourcePath() {
        return enemyType.getResourcePath();
    }
}
