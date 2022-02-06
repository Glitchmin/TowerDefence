package Code.map_handling;

import Code.Vector2d;

public class PathTile {
    private final Vector2d pos;
    private final MoveDirection dir;

    public PathTile(Vector2d vector2d, MoveDirection moveDirection) {
        this.pos = vector2d;
        this.dir = moveDirection;
    }

    @Override
    public String toString() {
        return pos + " " + dir;
    }

    public Vector2d getPos() {
        return pos;
    }

    public MoveDirection getDir() {
        return dir;
    }
}
