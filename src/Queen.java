import java.awt.*;
import java.util.*;

public class Queen extends Piece {

    Queen (Color color) {
        super(color);
        this.imagePath = createPath(color, "Queen");
    }

    public ArrayList<Coord> getPossibleMoves(Coord c, Board b) {
        ArrayList<Coord> toReturn = new ArrayList<>();
        addOrthogonal(toReturn, c, b);
        addDiagonal(toReturn, c, b);
        return toReturn;
    }
}
