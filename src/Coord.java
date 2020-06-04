import javalib.worldimages.Posn;

public class Coord {
    public int x;
    public int y;

    Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isValid() {
        return x < 8 && y < 8 && x > -1 && y > -1;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Coord)) {
            return false;
        } else {
            Coord that = (Coord)other;
            return this.x == that.x && this.y == that.y;
        }
    }

    public int hashCode() {
        return 10000 * x + y;
    }
}
