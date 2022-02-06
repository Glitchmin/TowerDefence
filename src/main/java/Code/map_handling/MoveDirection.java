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

    public static MoveDirection getFromV2d(Vector2d vector2d) {
        if (vector2d.equals(new Vector2d(1.0, 0.0))) {
            return RIGHT;
        }
        if (vector2d.equals(new Vector2d(0.0, 1.0))) {
            return DOWN;
        }
        if (vector2d.equals(new Vector2d(-1.0, 0.0))) {
            return LEFT;
        }
        if (vector2d.equals(new Vector2d(0.0, -1.0))) {
            return UP;
        }
        return null;
    }
}
