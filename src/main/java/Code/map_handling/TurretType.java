package Code.map_handling;

import Code.map_handling.turrets.LaserTurret;

public enum TurretType {
    LASER,
    MORTAR,
    ELECTRIC;

    public AbstractTurret getNewTurret() {
        return switch (this) {
            case LASER -> new LaserTurret();
            default -> new LaserTurret();
        };
    }
}
