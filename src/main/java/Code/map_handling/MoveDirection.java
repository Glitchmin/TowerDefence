package Code.map_handling;

import Code.Vector2d;

public enum MoveDirection {
    RIGHT,
    DOWN,
    LEFT,
    UP;

    public Vector2d getVector2d() {
        return switch (this) {
            case RIGHT -> new Vector2d(1.0, 0.0);
            case DOWN -> new Vector2d(0.0, 1.0);
            case LEFT -> new Vector2d(-1.0, 0.0);
            case UP -> new Vector2d(0.0, -1.0);
        };
    }
}
