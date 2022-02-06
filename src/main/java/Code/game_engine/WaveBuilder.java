package Code.game_engine;

import Code.map_handling.Enemy;
import Code.map_handling.EnemyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaveBuilder {
    private Integer waveCounter;
    private long lastTimeDeployed;
    private final long deployDelay;
    private int goldForWave;
    private final List<EnemyType> enemies;
    private final Random random;

    public WaveBuilder() {
        enemies = new ArrayList<>();
        goldForWave = 60;
        waveCounter = 0;
        deployDelay = 200;
        random = new Random();
    }

    public void newWave() {
        waveCounter++;
        int goldToSpend = goldForWave;
        int min_cost = new Enemy(EnemyType.WALKER).getGoldDrop();
        while (goldToSpend >= min_cost) {
            Enemy enemyToAdd = new Enemy(EnemyType.values()[random.nextInt(EnemyType.values().length)]);
            if (enemyToAdd.getGoldDrop() <= goldToSpend) {
                enemies.add(enemyToAdd.getEnemyType());
                goldToSpend-=enemyToAdd.getGoldDrop();
            }
        }
        goldForWave = (int) (goldForWave * 1.2);
    }

    public EnemyType getEnemy(long currentTime) {
        if (currentTime-lastTimeDeployed >= deployDelay) {
            if (!enemies.isEmpty()) {
                lastTimeDeployed = currentTime;
                EnemyType enemyType = enemies.get(0);
                enemies.remove(0);
                return enemyType;
            }
        }
        return null;
    }

    public Integer getWaveCounter() {
        return waveCounter;
    }
}
