package Code.map_handling;

import Code.Vector2d;
import Code.map_handling.turrets.ElectricTurret;
import Code.map_handling.turrets.LaserTurret;

public enum TurretType {
    LASER,
    ELECTRIC;

    public AbstractTurret getNewTurret(Vector2d position,boolean isOnHill) {
        return switch (this) {
            case LASER -> new LaserTurret(position, isOnHill);
            case ELECTRIC -> new ElectricTurret(position, isOnHill);
        };
    }
    public static TurretType turretTypeFromClass(AbstractTurret turret){
        if (turret instanceof LaserTurret){
            return LASER;
        }
        if (turret instanceof ElectricTurret){
            return ELECTRIC;
        }
        return null;
    }
}
