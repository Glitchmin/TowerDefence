package Code.map_handling;

public class Map {
    private final Integer width;
    private final Integer height;
    private final Landscape[][] landscapesArray;
    //private final MapObject[][] mapObjectsArray;

    public Map(Integer width, Integer height) {
        this.width = width;
        this.height = height;
        landscapesArray = new Landscape[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                landscapesArray[i][j] = new Landscape(LandscapeType.GRASS);
            }
        }
    }

    public Landscape getLandscape(int x, int y){
        return landscapesArray[x][y];
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}
