package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	List<ChessPiece> _piecesOnTheBoard = new ArrayList<ChessPiece>();
	List<ChessPiece> _capturedPieces = new ArrayList<ChessPiece>();

	private int turn;
	private Color currentPlayer;
	private Board board;

	public ChessMatch() {
		this.board = new Board(8, 8);
		this.setTurn(1);
		this.setCurrentPlayer(Color.WHITE);
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public void setCurrentPlayer(Color player) {
		this.currentPlayer = player;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] match = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				match[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return match;
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	public ChessPiece performChessMove(ChessPosition soucePosition, ChessPosition targetPosition) {
		Position source = soucePosition.toPosition();
		Position target = targetPosition.toPosition();

		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		this.nextTurn();
		return (ChessPiece) capturedPiece;
	}

	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		if (capturedPiece!= null) {
			_piecesOnTheBoard.remove(capturedPiece);
			_capturedPieces.add((ChessPiece)capturedPiece);
		}
		return capturedPiece;
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if (this.getCurrentPlayer() != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}

	private void nextTurn() {
		this.setTurn(getTurn() + 1);
		this.setCurrentPlayer(this.getCurrentPlayer() == Color.WHITE ? Color.BLACK : Color.WHITE);
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		_piecesOnTheBoard.add(piece);
	}

	public void initialSetup() {
		placeNewPiece('b', 1, new Rook(board, Color.WHITE));
		placeNewPiece('e', 8, new King(board, Color.BLACK));
		placeNewPiece('e', 1, new King(board, Color.WHITE));
	}
}
