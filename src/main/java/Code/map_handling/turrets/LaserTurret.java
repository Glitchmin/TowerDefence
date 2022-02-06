package Code.map_handling.turrets;

import Code.Vector2d;
import Code.map_handling.AbstractTurret;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class LaserTurret extends AbstractTurret {
    private Double dmg;
    private Double rpm;
    private long lastTimeFired;

    public LaserTurret(Vector2d position, boolean isOnHill) {
        range = 2.0;
        if (isOnHill){
            range += 0.5;
        }
        cost = 50;
        dmg = 5.0;
        rpm = 60.0;
        this.position = position;
        this.isOnHill = isOnHill;
        targets = new ArrayList<>();
    }

    @Override
    public String getResourcePath() {
        return "src/main/resources/turrets/laser_turret.png";
    }

    @Override
    public String getTurretName() {
        return "Laser Turret";
    }

    @Override
    public VBox getDescriptionVBox() {
        String hillText="";
        if (isOnHill){
            hillText = "placed on a hill (+0.5 range)";
        }
        return new VBox(new Label("range: " + range), new Label("dmg: " + dmg),
                new Label("attack speed: " + rpm + " rpm"), new Label("cost: " + cost),
                new Label("level: " + level), new Label(hillText));
    }

    @Override
    public void upgrade() {
        range += 0.5;
        dmg += 2.0;
        rpm += 6.0;
        level += 1;
    }

    @Override
    public List<Vector2d> fire(long currentTime) {
        if (currentTime-lastTimeFired >= 1000*60/rpm) {
            if (!targets.isEmpty()) {
                targets.get(0).decreaseHp(dmg);
                lastTimeFired = currentTime;
                ArrayList<Vector2d> shotsList = new ArrayList<>();
                shotsList.add(targets.get(0).getPosition());
                return shotsList;
            }
        }
        return null;
    }
}
