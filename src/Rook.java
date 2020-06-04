import java.awt.*;
import java.util.*;

public class Rook extends Piece {
    boolean hasMoved;

    Rook (Color color) {
        super(color);
        this.imagePath = createPath(color, "Rook");
        hasMoved = false;
    }

    public ArrayList<Coord> getPossibleMoves(Coord c, Board b) {
        ArrayList<Coord> toReturn = new ArrayList<>();
        addOrthogonal(toReturn, c, b);
        return toReturn;
    }
}
