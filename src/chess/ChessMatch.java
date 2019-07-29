package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

    private Board board;
    private int turn;
    private Color currentPlayer;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;

    public ChessMatch() {
        this.board = new Board(8, 8);
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] match = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                match[i][j] = (ChessPiece) board.piece(i,j);
            }
        }
        return match;
    }

    public void initialSetup() {
    	board.placePiece(new Rook(board, Color.WHITE), new Position(2, 1));
    	board.placePiece(new King(board, Color.BLACK), new Position(0, 4));
    	board.placePiece(new Rook(board, Color.WHITE), new Position(3, 5));
    }
}
