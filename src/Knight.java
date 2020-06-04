import java.awt.*;
import java.util.*;

public class Knight extends Piece {

    Knight (Color color) {
        super(color);
        this.imagePath = createPath(color, "Knight");
    }

    public ArrayList<Coord> getPossibleMoves(Coord c, Board b) {
        ArrayList<Coord> toReturn = new ArrayList<>();
        addIfNotBlocked(toReturn, new Coord(c.x+2, c.y+1), b);
        addIfNotBlocked(toReturn, new Coord(c.x+2, c.y-1), b);
        addIfNotBlocked(toReturn, new Coord(c.x-2, c.y+1), b);
        addIfNotBlocked(toReturn, new Coord(c.x-2, c.y-1), b);
        addIfNotBlocked(toReturn, new Coord(c.x+1, c.y+2), b);
        addIfNotBlocked(toReturn, new Coord(c.x+1, c.y-2), b);
        addIfNotBlocked(toReturn, new Coord(c.x-1, c.y+2), b);
        addIfNotBlocked(toReturn, new Coord(c.x-1, c.y-2), b);
        return toReturn;
    }
}
