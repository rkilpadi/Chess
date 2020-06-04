import java.awt.*;
import java.util.*;

public class Bishop extends Piece {

    Bishop (Color color) {
        super(color);
        this.imagePath = createPath(color, "Bishop");
    }

    public ArrayList<Coord> getPossibleMoves(Coord c, Board b) {
        ArrayList<Coord> toReturn = new ArrayList<>();
        addDiagonal(toReturn, c, b);
        return toReturn;
    }
}
