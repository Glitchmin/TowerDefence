package Code.game_engine;

import Code.PlayerValues;
import Code.Vector2d;
import Code.gui.IEnemyChangeObserver;
import Code.gui.MapVisualizer;
import Code.map_handling.AbstractTurret;
import Code.map_handling.Enemy;
import Code.map_handling.EnemyType;
import Code.map_handling.Map;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import static java.lang.System.out;

public class MainLoop implements Runnable {
    private final PlayerValues playerValues;
    private final MapVisualizer mapVisualizer;
    private final Map map;
    private final List<IEnemyChangeObserver> enemyChangeObservers;
    private final Random random;


    public MainLoop(PlayerValues playerValues, MapVisualizer mapVisualizer, Map map) {
        this.playerValues = playerValues;
        this.mapVisualizer = mapVisualizer;
        this.map = map;
        this.random = new Random();
        this.enemyChangeObservers = new ArrayList<>();
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
        List<Enemy> enemiesToDelete = new ArrayList<>();
        for (Enemy enemy : map.getEnemies()) {
            if (!enemy.reachedEnd()) {
                enemy.move(System.currentTimeMillis());
            } else {
                playerValues.dealDmg(enemy.getDmg());
                enemiesToDelete.add(enemy);
            }
            enemyChanged(enemy);
        }
        for (Enemy enemy : enemiesToDelete) {
            map.removeEnemy(enemy);
        }
    }

    public void calcTurrets(){
        for (AbstractTurret turret : map.getTurretsList()){
            Vector2d firePos = turret.fire();
            if (firePos != null) {
                mapVisualizer.addALine(turret.getPosition(), firePos);
            }
        }
    }


    @Override
    public void run() {
        map.addEnemy(new Enemy(EnemyType.RUNNER, System.currentTimeMillis(), map, map.getTurretsList()));
        map.addEnemy(new Enemy(EnemyType.RUNNER, System.currentTimeMillis(), map, map.getTurretsList()));
        while (!playerValues.isPlayerDead()) {
            moveEnemies();
            calcTurrets();
            Platform.runLater(mapVisualizer);
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                out.println("Interrupted Threat Simulation Engine");
                e.printStackTrace();
            }

        }
    }
}

