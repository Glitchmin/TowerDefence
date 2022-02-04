package Code;

import java.util.Objects;

public class Vector2d {
    public Double x;
    final public Double y;

    public Vector2d(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + "," + y + ")";
    }

    public boolean precedes(Vector2d other) {
        return (this.x <= other.x && this.y <= other.y);
    }

    public boolean follows(Vector2d other) {
        return (this.x >= other.x && this.y >= other.y);
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public Integer IntX() {
        return this.x.intValue();
    }

    public Integer IntY() {
        return this.y.intValue();
    }

    public boolean equals(Vector2d other){
        return (Objects.equals(other.x, this.x) && Objects.equals(other.y, this.y));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}
