package Code.map_handling;

import Code.Vector2d;

public class Enemy extends AbstractMapObject {
    private final Double speed;
    private Double hp;
    private final Integer goldDrop;
    private final Vector2d position;
    private final EnemyType enemyType;
    private static Integer counter = 0;
    private final Integer ID;

    public Enemy(EnemyType enemyType) {
        position = new Vector2d(0.0,5.0);
        this.enemyType = enemyType;
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

    @Override
    public String getResourcePath() {
        return enemyType.getResourcePath();
    }
}
