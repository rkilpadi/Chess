import java.awt.*;
import java.util.*;

public class Pawn extends Piece {
    // can en passant be performed on this pawn?
    boolean passable;

    Pawn (Color color) {
        super(color);
        this.imagePath = createPath(color, "Pawn");
    }

    public ArrayList<Coord> getPossibleMoves(Coord c, Board b) {
        ArrayList<Coord> toReturn = new ArrayList<>();
        if (color.equals(Color.WHITE)) {
            addIfEmpty(toReturn, new Coord(c.x, c.y-1), b);
            // if pawn hasn't moved, add two squares ahead
            if (c.y == 6 && toReturn.size() == 1) {
                addIfEmpty(toReturn, new Coord(c.x, c.y-2), b);
            }
            // attacking
            addIfEnemy(toReturn, new Coord(c.x-1, c.y-1), b);
            addIfEnemy(toReturn, new Coord(c.x+1, c.y-1), b);
        // mirrored for black
        } else {
            addIfEmpty(toReturn, new Coord(c.x, c.y+1), b);
            if (c.y == 1 && toReturn.size() == 1) {
                addIfEmpty(toReturn, new Coord(c.x, c.y+2), b);
            }
            addIfEnemy(toReturn, new Coord(c.x-1, c.y+1), b);
            addIfEnemy(toReturn, new Coord(c.x+1, c.y+1), b);
        }
        Optional.ofNullable(b.getPiece(new Coord(c.x+1, c.y))).ifPresent(piece -> addEnPassant(piece, toReturn, b));
        Optional.ofNullable(b.getPiece(new Coord(c.x-1, c.y))).ifPresent(piece -> addEnPassant(piece, toReturn, b));
        return toReturn;
    }

    // add en passant as a move if applicable
    void addEnPassant(Piece neighbor, ArrayList<Coord> l, Board b) {
        if (neighbor instanceof Pawn) {
            if (((Pawn) neighbor).passable && !neighbor.color.equals(this.color)) {
                if (neighbor.color.equals(Color.WHITE)) {
                    l.add(new Coord(b.getCoord(neighbor).x, 5));
                } else {
                    l.add(new Coord(b.getCoord(neighbor).x, 2));
                }
            }
        }
    }

}
