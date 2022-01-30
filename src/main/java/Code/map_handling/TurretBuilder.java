package Code.map_handling;

import Code.gui.MapVisualizer;
import Code.gui.TurretShop;

import static java.lang.System.out;

public class TurretBuilder {
    private Map map;
    private TurretShop turretShop;
    public TurretBuilder(Map map, TurretShop turretShop){
        this.map = map;
        this.turretShop = turretShop;
    }
    public void build(AbstractTurret turret){
        out.println(turret);
    }
}
