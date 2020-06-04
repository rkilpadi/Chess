import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.*;

public class Game extends World {

    Board board;
    Color player;
    static final int SIZE = 50; // of chess square
    Piece selectedPiece;
    String message; // to be displayed (e.g. "check")

    Game() {
        board = new Board();
        player = Color.WHITE;
        selectedPiece = board.ghost;
        message = "";
    }

    public WorldScene makeScene() {
        WorldScene scene = makeBoard();
        for (Piece p : board.pieceToCoord.keySet()) {
            scene.placeImageXY(new FromFileImage(p.imagePath),
                    coordToSize(board.pieceToCoord.get(p).x), coordToSize(board.pieceToCoord.get(p).y));
        }
        scene = showPossibleMoves(scene);
        scene.placeImageXY(new TextImage(message, SIZE/2, Color.RED), SIZE*4, SIZE*4);
        return scene;
    }

    // Turns a chess coordinate (between 1 - 8) into a posn on the scene
    int coordToSize(int coord) {
        return coord * SIZE + SIZE/2;
    }

    // inverse of coordToSize
    int sizeToCoord(int size) {
        return size / SIZE;
    }

    WorldScene makeBoard() {
        WorldScene board = getEmptyScene();

        for (int xCoord = 0; xCoord < 8; xCoord++) {
            for (int yCoord = 0; yCoord < 8; yCoord++) {

                board.placeImageXY(
                        new RectangleImage(SIZE, SIZE, OutlineMode.SOLID, getSquareColor(xCoord, yCoord)),
                        coordToSize(xCoord), coordToSize(yCoord));

            }
        }
        return board;
    }

    Color getSquareColor(int x, int y) {
        if ((x % 2 == 0) == (y % 2 == 0)) {
                return Color.WHITE;
        } else {
            return Color.DARK_GRAY;
        }
    }

    WorldScene showPossibleMoves(WorldScene scene) {
        // circles for possible moves
        for (Coord c : selectedPiece.getPossibleMoves(board.pieceToCoord.get(selectedPiece), board)) {
            scene.placeImageXY(
                    new CircleImage(SIZE / 4, OutlineMode.SOLID, Color.RED),
                    coordToSize(c.x), coordToSize(c.y));
        }
        return scene;
    }

    static Color getEnemy(Color color) {
        if (color.equals(Color.BLACK)) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    public void onMouseClicked(Posn mouse) {
        Coord selected = new Coord(sizeToCoord(mouse.x), sizeToCoord(mouse.y));

        if (selectedPiece.getPossibleMoves(board.pieceToCoord.get(selectedPiece), board).contains(selected)) {
            move(selectedPiece, selected);
            turnOver();
        }

        Piece potentialSelected = board.coordToPiece.getOrDefault(selected, board.ghost);
        if (player.equals(potentialSelected.color)) {
            selectedPiece = potentialSelected;
        } else {
            selectedPiece = board.ghost;
        }
    }

    void turnOver() {
        if (inCheck(player, board.findKing(player))) {
            board.undoMove();
            message = "illegal move: in check!";
            return;
        }
        selectedPiece = board.ghost;
        player = getEnemy(player);
        board.ghostRecent();
        message = "";
    }

    void move(Piece p, Coord c) {
        // manually removes pawn after en passant
        if (p instanceof Pawn) {
            if (c.x != board.getCoord(p).x && c.y != board.getCoord(p).y
                && Optional.ofNullable(board.getPiece(c)).isEmpty()) {
                   board.pieceToCoord.remove(clearPassable());
            }
        // moves rook after castling
        } else if (p instanceof King) {
            if (Math.abs(board.getCoord(p).x - c.x) == 2) {
                if (c.x == 6) {
                    move(board.getPiece(new Coord(7, c.y)), new Coord(5, c.y));
                } else if (c.x == 2) {
                    move(board.getPiece(new Coord(0, c.y)), new Coord(3, c.y));
                }
            }
        }
        clearPassable();
        board.put(p, c);
        // update values after move
        if (p instanceof King) {
            ((King) p).hasMoved = true;
        } else if (p instanceof Rook) {
            ((Rook) p).hasMoved = true;
        } else if (p instanceof Pawn) {
            if (p.color.equals(Color.WHITE)) {
                ((Pawn) p).passable = board.getCoord(p).y == 4;
            } else {
                ((Pawn) p).passable = board.getCoord(p).y == 3;
            }
        }
    }

    // makes all pawns impassible
    Piece clearPassable() {
        for (Piece p : board.pieceToCoord.keySet()) {
            if (p instanceof Pawn) {
                if (((Pawn) p).passable) {
                    ((Pawn) p).passable = false;
                    return p;
                }
            }
        }
        return board.ghost;
    }

    boolean inCheck(Color player, Coord kingCoord) {
        for (Piece p : board.pieceToCoord.keySet()) {
            if (!p.color.equals(player)) {
                if (p.getPossibleMoves(board.pieceToCoord.get(p), board).contains(kingCoord)) {
                    return true;
                }
            }
        }
        return false;
    }
}
