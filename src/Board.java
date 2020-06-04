import java.awt.Color;
import java.util.*;


public class Board {
    // no BiMaps :(
    Map<Piece, Coord> pieceToCoord;
    Map<Coord, Piece> coordToPiece;
    ArrayList<Piece> threats;

    AbstractMap.SimpleEntry<Piece, Coord> recentlyTaken;
    AbstractMap.SimpleEntry<Piece, Coord> lastMove; // last posn

    // Piece with no moves to represent null
    Piece ghost = new Piece(Color.WHITE) {
        public ArrayList<Coord> getPossibleMoves(Coord c, Board b) {
            return new ArrayList<>();
        }
    };

    Board() {
        pieceToCoord = new HashMap<>();
        coordToPiece = new HashMap<>();
        threats = new ArrayList<>();
        setUp();
    }

    boolean containsPiece(Piece p) {
        return pieceToCoord.containsKey(p);
    }

    boolean containsCoord(Coord c) {
        return pieceToCoord.containsValue(c);
    }

    Coord getCoord(Piece p) {
        return pieceToCoord.get(p);
    }

    Piece getPiece(Coord c) {
        return coordToPiece.get(c);
    }

    // updates maps by adding new location and removing old
    // updates lastMove and recentlyTaken
    void put(Piece p, Coord c) {
        if (containsCoord(c)) {
            recentlyTaken = new AbstractMap.SimpleEntry<>(getPiece(c), c);
            pieceToCoord.remove(getPiece(c));
        }
        Coord oldCoord = getCoord(p);
        lastMove = new AbstractMap.SimpleEntry<>(p, oldCoord);
        pieceToCoord.put(p, c);
        coordToPiece.put(c, p);
        coordToPiece.remove(oldCoord);
    }

    void undoMove() {
        put(lastMove.getKey(), lastMove.getValue());
        if (!recentlyTaken.getKey().equals(ghost)) {
            put(recentlyTaken.getKey(), recentlyTaken.getValue());
        }
    }

    // return Coord of a given player's king
    Coord findKing(Color player) {
        for (Piece p : pieceToCoord.keySet()) {
            if (p.color.equals(player)) {
                if (p instanceof King) {
                    return getCoord(p);
                }
            }
        }
        throw new RuntimeException("King not found");
    }

    // if no piece was recently taken
    void ghostRecent() {
        recentlyTaken = new AbstractMap.SimpleEntry<>(ghost, new Coord(0,0));
    }

    // returns a chess board that is set up
    void setUp() {
        // Pawns
        for (int i = 0; i < 8; i++) {
            put(new Pawn(Color.WHITE), new Coord(i, 6));
        }
        for (int i = 0; i < 8; i++) {
            put(new Pawn(Color.BLACK), new Coord(i, 1));
        }
        // Rooks
        put(new Rook(Color.BLACK), new Coord(0, 0));
        put(new Rook(Color.BLACK), new Coord(7, 0));
        put(new Rook(Color.WHITE), new Coord(0, 7));
        put(new Rook(Color.WHITE), new Coord(7, 7));
        // Knights
        put(new Knight(Color.BLACK), new Coord(1, 0));
        put(new Knight(Color.BLACK), new Coord(6, 0));
        put(new Knight(Color.WHITE), new Coord(1, 7));
        put(new Knight(Color.WHITE), new Coord(6, 7));
        // Bishops
        put(new Bishop(Color.BLACK), new Coord(2, 0));
        put(new Bishop(Color.BLACK), new Coord(5, 0));
        put(new Bishop(Color.WHITE), new Coord(2, 7));
        put(new Bishop(Color.WHITE), new Coord(5, 7));
        // Queens
        put(new Queen(Color.BLACK), new Coord(3, 0));
        put(new Queen(Color.WHITE), new Coord(3, 7));
        // Kings
        put(new King(Color.BLACK), new Coord (4, 0));
        put(new King(Color.WHITE), new Coord (4, 7));
    }
}
