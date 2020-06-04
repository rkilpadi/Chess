import java.awt.*;
import java.util.*;
import java.util.function.UnaryOperator;

public abstract class Piece {
    Color color;
    String imagePath;

    public abstract ArrayList<Coord> getPossibleMoves(Coord c, Board b);

    Piece(Color color) {
        if (color != Color.WHITE && color != Color.BLACK) {
            throw new IllegalArgumentException("Color must be white or black");
        }
        this.color = color;
    }

    public String createPath(Color c, String Name) {
        String color = (c == Color.WHITE ? "white" : "black");
        return "resources/" + color + Name + ".png";
    }

    // returns true if the Piece on the given Coord is an enemy to this
    boolean otherColor(Coord c, Board b) {
        if (b.containsCoord(c)) {
            return !b.coordToPiece.get(c).color.equals(this.color);
        }
        return false;
    }

    public void addIfEmpty(ArrayList<Coord> list, Coord c, Board b) {
        if (c.isValid() && !(b.containsCoord(c))) {
            list.add(c);
        }
    }

    // adds Coord if an ally is not on it
    public void addIfNotBlocked(ArrayList<Coord> list, Coord c, Board b) {
        if (c.isValid()) {
            if (otherColor(c, b)) {
                list.add(c);
            } else if (!b.containsCoord(c)){
                list.add(c);
            }
        }
    }

    // adds Coord if it is an enemy (for pawns)
    public void addIfEnemy(ArrayList<Coord> list, Coord c, Board b) {
        if (c.isValid() && otherColor(c, b)) {
            list.add(c);
        }
    }

    public void addDirection(ArrayList<Coord> list, Coord c,
            Board b, UnaryOperator<Coord> increment) {

        Coord next = increment.apply(c);

        // add until reaches another piece or an invalid coord
        while (next.isValid() && !b.containsCoord(next)) {
            list.add(next);
            next = increment.apply(next);
        }
        // adds one enemy piece (to take) to possible moves
        addIfEnemy(list, next, b);
    }

    // up, down, left, right
    public void addOrthogonal(ArrayList<Coord> list, Coord c, Board b) {
        addDirection(list, c, b, p -> new Coord(p.x, p.y+1));
        addDirection(list, c, b, p -> new Coord(p.x, p.y-1));
        addDirection(list, c, b, p -> new Coord(p.x+1, p.y));
        addDirection(list, c, b, p -> new Coord(p.x-1, p.y));
    }

    public void  addDiagonal(ArrayList<Coord> list, Coord c, Board b) {
        addDirection(list, c, b, p -> new Coord(p.x+1, p.y+1));
        addDirection(list, c, b, p -> new Coord(p.x-1, p.y+1));
        addDirection(list, c, b, p -> new Coord(p.x+1, p.y-1));
        addDirection(list, c, b, p -> new Coord(p.x-1, p.y-1));
    }
}
