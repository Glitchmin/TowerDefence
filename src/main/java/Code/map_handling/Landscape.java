package Code.map_handling;

public class Landscape extends MapObject{
    LandscapeType landscapeType;
    public Landscape(LandscapeType landscapeType){
        this.landscapeType = landscapeType;
    }

    @Override
    public String getResourcePath() {
        return landscapeType.getResourcePath();
    }
}
