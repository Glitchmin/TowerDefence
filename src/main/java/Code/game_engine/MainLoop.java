package Code.game_engine;

import Code.PlayerValues;
import Code.gui.MapVisualizer;
import Code.map_handling.Enemy;
import Code.map_handling.EnemyType;
import javafx.application.Platform;

import static java.lang.System.out;

public class MainLoop implements Runnable {
    final PlayerValues playerValues;
    final MapVisualizer mapVisualizer;

    public MainLoop(PlayerValues playerValues, MapVisualizer mapVisualizer) {
        this.playerValues = playerValues;
        this.mapVisualizer = mapVisualizer;
    }

    private void addEnemies() {
    }

    @Override
    public void run() {
        while (!playerValues.isPlayerDead()) {
            Platform.runLater(mapVisualizer);
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

