package Code.game_engine;

import Code.PlayerValues;
import Code.Vector2d;
import Code.gui.IEnemyChangeObserver;
import Code.gui.MapVisualizer;
import Code.map_handling.*;
import Code.map_handling.turrets.LaserTurret;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

public class MainLoop implements Runnable {
    private final PlayerValues playerValues;
    private final MapVisualizer mapVisualizer;
    private final Map map;
    private final Random random;
    public final WaveBuilder waveBuilder;
    private final MeteorHandler meteorHandler;
    private boolean isStarted;

    public MainLoop(PlayerValues playerValues, MapVisualizer mapVisualizer, Map map) {
        this.playerValues = playerValues;
        this.mapVisualizer = mapVisualizer;
        this.map = map;
        this.random = new Random();
        this.meteorHandler = new MeteorHandler(System.currentTimeMillis(), map);
        meteorHandler.addObserver(mapVisualizer);
        waveBuilder = new WaveBuilder();
        isStarted = false;
    }

    private void addNewEnemies() {
        if (isStarted) {
            EnemyType enemyType = waveBuilder.getEnemy(System.currentTimeMillis());
            if (enemyType != null) {
                Enemy enemy = new Enemy(enemyType, waveBuilder.getWaveCounter(), System.currentTimeMillis(), map, map.getTurretsList());
                enemy.addObserver(mapVisualizer);
                map.addEnemy(enemy);
            }
            if (map.getEnemies().isEmpty()) {
                waveBuilder.newWave();
                playerValues.setWaveNumber(waveBuilder.getWaveCounter());
            }
        }
    }


    private void moveEnemies() {
        List<Enemy> enemiesToDelete = new ArrayList<>();
        for (Enemy enemy : map.getEnemies()) {
            if (!(enemy.reachedEnd() || enemy.isDead())) {
                enemy.move(System.currentTimeMillis());
            } else {
                if (enemy.reachedEnd()) {
                    playerValues.dealDmg(enemy.getDmg());
                }
                if (enemy.isDead()) {
                    playerValues.addGold(enemy.getGoldDrop());
                }
                enemiesToDelete.add(enemy);
            }
            enemy.positionChanged();
        }
        for (Enemy enemy : enemiesToDelete) {
            map.removeEnemy(enemy);
        }
    }

    public void start() {
        isStarted = true;
    }

    public void calcTurrets() {
        for (AbstractTurret turret : map.getTurretsList()) {
            List<Vector2d> firePos = turret.fire(currentTimeMillis());
            if (firePos != null) {
                for (Vector2d pos : firePos) {
                    if (turret instanceof LaserTurret) {
                        mapVisualizer.addLine(turret.getPosition(), pos, Color.RED);
                    }else{
                        mapVisualizer.addLine(turret.getPosition(), pos, Color.YELLOW);
                    }
                }
            }
        }
    }


    @Override
    public void run() {
        while (!playerValues.isPlayerDead()) {
            mapVisualizer.updateTime(System.currentTimeMillis());
            addNewEnemies();
            moveEnemies();
            calcTurrets();
            meteorHandler.calcMeteors(System.currentTimeMillis(), map.getMeteorListAndClear());
            Platform.runLater(mapVisualizer);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                out.println("Interrupted Threat Simulation Engine");
                e.printStackTrace();
            }

        }
    }
}

