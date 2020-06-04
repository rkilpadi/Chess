import java.awt.*;
import java.util.*;

public class King extends Piece {
    boolean hasMoved;

    King (Color color) {
        super(color);
        this.imagePath = createPath(color, "King");
        hasMoved = false;
    }

    public ArrayList<Coord> getPossibleMoves(Coord c, Board b) {
        ArrayList<Coord> toReturn = new ArrayList<>();
        addIfNotBlocked(toReturn, new Coord(c.x+1, c.y+1), b);
        addIfNotBlocked(toReturn, new Coord(c.x+1, c.y), b);
        addIfNotBlocked(toReturn, new Coord(c.x+1, c.y-1), b);
        addIfNotBlocked(toReturn, new Coord(c.x, c.y+1), b);
        addIfNotBlocked(toReturn, new Coord(c.x, c.y-1), b);
        addIfNotBlocked(toReturn, new Coord(c.x-1, c.y+1), b);
        addIfNotBlocked(toReturn, new Coord(c.x-1, c.y), b);
        addIfNotBlocked(toReturn, new Coord(c.x-1, c.y-1), b);
        if (!hasMoved) {
            addCastling(toReturn, c, b);
        }
        return toReturn;
    }

    void addCastling(ArrayList<Coord> l, Coord c, Board b) {
        // King side
        if (l.contains(new Coord(c.x+1, c.y))) {
            Piece rightRook = Optional.ofNullable(b.getPiece(new Coord(c.x+3, c.y))).orElse(b.ghost);
            if (rightRook instanceof Rook) {
                if (!((Rook) rightRook).hasMoved) {
                    addIfNotBlocked(l, new Coord(c.x + 2, c.y), b);
                }
            }
        // Queen side
        } else if (l.contains(new Coord(c.x-1, c.y)) && !b.containsCoord(new Coord(c.x-3, c.y))) {
            Piece leftRook = Optional.ofNullable(b.getPiece(new Coord(c.x-4, c.y))).orElse(b.ghost);
            if (leftRook instanceof Rook) {
                if (!((Rook) leftRook).hasMoved) {
                    addIfNotBlocked(l, new Coord(c.x-2, c.y), b);
                }
            }
        }
    }
}
