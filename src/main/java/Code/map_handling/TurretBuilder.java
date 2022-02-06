package Code.map_handling;

import Code.PlayerValues;
import Code.gui.ITurretChangeObserver;

import java.util.ArrayList;
import java.util.List;

public class TurretBuilder {
    private Map map;
    private PlayerValues playerValues;
    private final List<ITurretChangeObserver> observersList;

    public TurretBuilder(Map map, PlayerValues playerValues) {
        this.map = map;
        this.playerValues = playerValues;
        observersList = new ArrayList<>();
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
}
