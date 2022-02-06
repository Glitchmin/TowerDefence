package Code.map_handling;

public enum EnemyType {
    WALKER,
    RUNNER,
    SWIMMER,
    TANK;

    public String getResourcePath() {
        return switch (this) {
            case WALKER -> "src/main/resources/enemies/walker.png";
            case RUNNER -> "src/main/resources/enemies/runner.png";
            case SWIMMER -> "src/main/resources/enemies/swimmer.png";
            case TANK -> "src/main/resources/enemies/tank.png";
        };
    }

}
