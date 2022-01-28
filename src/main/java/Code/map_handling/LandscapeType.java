package Code.map_handling;

public enum LandscapeType {
    GRASS,
    PATH,
    HILL,
    RIVER;
    public String getResourcePath(){
        return switch(this){
            case GRASS -> "src/main/resources/landscape/grass.png";
            case PATH -> "src/main/resources/landscape/path.png";
            case HILL -> "src/main/resources/landscape/hill.png";
            case RIVER -> "src/main/resources/landscape/river.png";
        };
    }
}

