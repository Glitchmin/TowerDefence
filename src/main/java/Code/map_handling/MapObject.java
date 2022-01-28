package Code.map_handling;
import Code.Vector2d;
public abstract class MapObject {
    private Vector2d position;

    public Vector2d getPosition(){
        return position;
    }

    public abstract String getResourcePath();
}
