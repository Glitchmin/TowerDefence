package Code.map_handling;

public enum EnemyType {
    WALKER,
    RUNNER,
    TANK;

    public String getResourcePath() {
        return switch (this) {
            case WALKER -> "src/main/resources/enemies/walker.png";
            default -> "src/main/resources/enemies/walker.png";
        };
    }
}
