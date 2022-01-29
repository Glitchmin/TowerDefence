package Code.map_handling;

public class Landscape extends AbstractMapObject {
    public LandscapeType landscapeType;
    public Landscape(LandscapeType landscapeType){
        this.landscapeType = landscapeType;
    }

    @Override
    public String getResourcePath() {
        return landscapeType.getResourcePath();
    }

}
