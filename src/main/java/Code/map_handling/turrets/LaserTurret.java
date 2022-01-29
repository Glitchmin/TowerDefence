package Code.map_handling.turrets;

import Code.map_handling.AbstractMapObject;
import Code.map_handling.AbstractTurret;

public class LaserTurret extends AbstractTurret {
    public LaserTurret(){
        range = 2.0;
        cost = 50;
        width = 1;
        height = 1;
    }
    @Override
    public String getResourcePath() {
        return "src/main/resources/turrets/laser_turret.png";
    }
}
