package Code.map_handling;

import Code.PlayerValues;
import Code.gui.MapVisualizer;
import Code.gui.TurretShop;

import static java.lang.System.out;

public class TurretBuilder {
    private Map map;
    private TurretShop turretShop;
    private PlayerValues playerValues;

    public TurretBuilder(Map map, TurretShop turretShop, PlayerValues playerValues) {
        this.map = map;
        this.turretShop = turretShop;
        this.playerValues = playerValues;
    }

    public void build(int x, int y, AbstractTurret turret) {
        if (playerValues.getGold() >= turret.cost) {
            playerValues.removeGold(turret.cost);
            out.println(playerValues.getGold());
            map.buildTurret(x, y, turret);
        }
    }
}
