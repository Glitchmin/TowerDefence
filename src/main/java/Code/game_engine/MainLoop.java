package Code.game_engine;

import Code.PlayerValues;
import Code.gui.MapVisualizer;
import Code.map_handling.Enemy;
import Code.map_handling.EnemyType;

import static java.lang.System.out;

public class MainLoop implements Runnable {
    final PlayerValues playerValues;
    final MapVisualizer mapVisualizer;

    public MainLoop(PlayerValues playerValues, MapVisualizer mapVisualizer) {
        this.playerValues = playerValues;
        this.mapVisualizer = mapVisualizer;
    }

    private void addEnemies() {
        mapVisualizer.addEnemy(new Enemy(EnemyType.RUNNER));
    }

    @Override
    public void run() {
        addEnemies();
        while (!playerValues.isPlayerDead()) {
                out.println("things happening");
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                out.println("Interrupted Threat Simulation Engine");
                e.printStackTrace();
            }

        }
    }
}

