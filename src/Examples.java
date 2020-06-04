import javalib.worldimages.Posn;
import tester.Tester;
import java.awt.*;
import java.util.*;

public class Examples {

    Game chessGame = new Game();

    void testBigBang(Tester t) {
        chessGame.bigBang(Game.SIZE*8, Game.SIZE*8);
    }

    void testCoordToSize(Tester t) {
        t.checkExpect(chessGame.board.pieceToCoord.size(), 32);
        t.checkExpect(new Pawn(Color.WHITE).getPossibleMoves(new Coord(6, 6), chessGame.board),
                Arrays.asList(new Coord(6, 5), new Coord(6, 4)));
        t.checkExpect(chessGame.sizeToCoord(160), 3);
    }
}
