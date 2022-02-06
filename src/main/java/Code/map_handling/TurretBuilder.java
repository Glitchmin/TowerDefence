package Code.map_handling;

import Code.PlayerValues;
import Code.gui.ITurretChangeObserver;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class TurretBuilder {
    private final Map map;
    private final PlayerValues playerValues;

    public TurretBuilder(Map map, PlayerValues playerValues) {
        this.map = map;
        this.playerValues = playerValues;
    }

    public void build(int x, int y, AbstractTurret turret) {
        if (playerValues.getGold() >= turret.cost) {
            playerValues.removeGold(turret.cost);
            turret.isOnMap = true;
            map.buildTurret(x, y, turret);
        }
    }


    public void upgrade(AbstractTurret turret) {
        if (playerValues.getGold() >= turret.cost) {
            playerValues.removeGold(turret.cost);
            turret.upgrade();
        }
    }
    public void multiUpgrade(AbstractTurret turret){
        while (playerValues.getGold() >= turret.cost) {
            upgrade(turret);
        }
    }
}
