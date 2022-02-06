package Code.map_handling.turrets;

import Code.Vector2d;
import Code.map_handling.AbstractTurret;
import Code.map_handling.Enemy;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.*;

import static java.lang.System.out;

public class ElectricTurret extends AbstractTurret {
    private Double dmg;
    private Double rpm;
    private long lastTimeFired;
    private Integer targetsAmount;

    public ElectricTurret(Vector2d position, boolean isOnHill) {
        range = 1.5;
        if (isOnHill) {
            range += 0.5;
        }
        cost = 60;
        dmg = 3.0;
        rpm = 120.0;
        targetsAmount = 3;
        this.position = position;
        targets = new ArrayList<>();
        this.isOnHill = isOnHill;
    }

    @Override
    public String getResourcePath() {
        return "src/main/resources/turrets/electric_turret.png";
    }

    @Override
    public String getTurretName() {
        return "Electric Turret";
    }

    @Override
    public List<Vector2d> fire(long currentTime) {
        if (currentTime - lastTimeFired >= 1000 * 60 / rpm) {
            List<Enemy> targetsList = new ArrayList<>();
            if (targets.size() <= targetsAmount) {
                targetsList.addAll(targets);
            } else {
                Random random = new Random();
                while (targetsList.size() < targetsAmount) {
                    Enemy enemy = targets.get(random.nextInt(targets.size()));
                    if (!targetsList.contains(enemy)) {
                        targetsList.add(enemy);
                    }
                }
            }
            lastTimeFired = currentTime;
            ArrayList<Vector2d> shotsList = new ArrayList<>();
            for (Enemy target : targetsList) {
                target.decreaseHp(dmg);
                shotsList.add(target.getPosition());
            }
            return shotsList;
        }
        return null;
    }

    @Override
    public VBox getDescriptionVBox() {
        String hillText = "";
        if (isOnHill) {
            hillText = "placed on a hill (+0.5 range)";
        }
        return new VBox(new Label("range: " + range), new Label("dmg: " + dmg),
                new Label("attack speed: " + rpm + " rpm"), new Label("cost: " + cost),
                new Label("targets amount: " + targetsAmount), new Label("level: " + level),
                new Label(hillText));
    }

    @Override
    public void upgrade() {
        range += 0.2;
        dmg += 0.5;
        rpm += 2.0;
        level += 1;
    }
}
