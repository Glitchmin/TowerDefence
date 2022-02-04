package Code.game_engine;

import Code.PlayerValues;
import Code.Vector2d;
import Code.gui.IEnemyChangeObserver;
import Code.gui.MapVisualizer;
import Code.map_handling.Enemy;
import Code.map_handling.EnemyType;
import Code.map_handling.Map;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static java.lang.System.out;

public class MainLoop implements Runnable {
    private final PlayerValues playerValues;
    private final MapVisualizer mapVisualizer;
    private final Map map;
    private final List<IEnemyChangeObserver> enemyChangeObservers;
    private final long startTime;


    public MainLoop(PlayerValues playerValues, MapVisualizer mapVisualizer, Map map) {
        this.playerValues = playerValues;
        this.mapVisualizer = mapVisualizer;
        this.map = map;
        this.enemyChangeObservers = new ArrayList<>();
        this.startTime = System.currentTimeMillis();
    }

    public void addEnemyChangeObserver(IEnemyChangeObserver enemyChangeObserver) {
        enemyChangeObservers.add(enemyChangeObserver);
    }

    public void enemyChanged(Enemy enemy) {
        for (IEnemyChangeObserver enemyChangeObserver : enemyChangeObservers) {
            enemyChangeObserver.enemyChanged(enemy);
        }
    }

    public void moveEnemies() {
        for (Enemy enemy : map.getEnemies()) {
            enemy.move(System.currentTimeMillis());
            enemyChanged(enemy);
        }
    }


    @Override
    public void run() {
        map.addEnemy(new Enemy(EnemyType.RUNNER, System.currentTimeMillis(), map));
        map.addEnemy(new Enemy(EnemyType.RUNNER, System.currentTimeMillis(), map));
        while (!playerValues.isPlayerDead()) {
            moveEnemies();
            Platform.runLater(mapVisualizer);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                out.println("Interrupted Threat Simulation Engine");
                e.printStackTrace();
            }

        }
    }
}

