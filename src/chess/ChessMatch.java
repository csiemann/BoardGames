package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

	List<ChessPiece> piecesOnTheBoard = new ArrayList<ChessPiece>();
	List<ChessPiece> capturedPieces = new ArrayList<ChessPiece>();

	private int turn;
	private ColorPlayer currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVunerable;
	private ChessPiece promoted;

	public ChessMatch() {
		this.board = new Board(8, 8);
		this.setTurn(1);
		this.setCurrentPlayer(ColorPlayer.WHITE);
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public ColorPlayer getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean isCheck() {
		return check;
	}

	public boolean isCheckMate() {
		return checkMate;
	}

	public ChessPiece getEnPassantVunerable() {
		return enPassantVunerable;
	}

	public ChessPiece getPromoted() {
		return promoted;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public void setCurrentPlayer(ColorPlayer player) {
		this.currentPlayer = player;
	}

	private void setCheck(boolean check) {
		this.check = check;
	}

	public void setCheckMate(boolean checkMate) {
		this.checkMate = checkMate;
	}

	private void setEnPassantVunerable(ChessPiece enPassantVunerable) {
		this.enPassantVunerable = enPassantVunerable;
	}

	private void setPromoted(ChessPiece promoted) {
		this.promoted = promoted;
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

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You don't put yourself in check");
		}

		ChessPiece movedPiece = (ChessPiece) board.piece(target);

		// #specialmove promotion
		setPromoted(null);
		if (movedPiece instanceof Pawn) {
			if ((movedPiece.getColor() == ColorPlayer.WHITE && target.getRow() == 0)
					|| (movedPiece.getColor() == ColorPlayer.BLACK && target.getRow() == 7)) {
				setPromoted((ChessPiece)board.piece(target));
				setPromoted(replacePromotedPiece("Q"));
			}
		}

		setCheck(testCheck(opponent(currentPlayer)));

		if (testCheckMate(opponent(currentPlayer))) {
			setCheckMate(true);
		} else {
			this.nextTurn();
		}

		// #specialmove en passant
		if (movedPiece instanceof Pawn
				&& (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2))
			this.setEnPassantVunerable(movedPiece);
		else
			setEnPassantVunerable(null);
		return (ChessPiece) capturedPiece;
	}

	public ChessPiece replacePromotedPiece(String type) {

		if (getPromoted()==null) {
			throw new IllegalStateException("There is no piece to be promoted");
		}
		if (!(type.equals("B")||type.equals("R")||type.equals("N")||type.equals("Q"))) {
			throw new InvalidParameterException("Invalid type for promotion");
		}

		Position pos = getPromoted().getChessPosition().toPosition();
		Piece p =board.removePiece(pos);
		piecesOnTheBoard.remove(p);

		ChessPiece newPiece = newPiece(type, getPromoted().getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);

		return newPiece;
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add((ChessPiece) capturedPiece);
		}

		// #specialmove castling kingside rook
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceR = new Position(source.getRow(), source.getColumn() + 3);
			Position targetR = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceR);
			board.placePiece(rook, targetR);
			rook.increaseMoveCount();
		}
		// #specialmove castling queenside rook
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceR = new Position(source.getRow(), source.getColumn() - 4);
			Position targetR = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceR);
			board.placePiece(rook, targetR);
			rook.increaseMoveCount();
		}

		// #specialmove en passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position pawnPosition;
				if (p.getColor() == ColorPlayer.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				} else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add((ChessPiece) capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}

		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add((ChessPiece) capturedPiece);
		}

		// #specialmove castling kingside rook
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceR = new Position(source.getRow(), source.getColumn() + 3);
			Position targetR = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetR);
			board.placePiece(rook, sourceR);
			rook.decreaseMoveCount();
		}
		// #specialmove castling queenside rook
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceR = new Position(source.getRow(), source.getColumn() - 4);
			Position targetR = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetR);
			board.placePiece(rook, sourceR);
			rook.decreaseMoveCount();
		}

		// #specialmove en passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == getEnPassantVunerable()) {
				ChessPiece pawn = (ChessPiece) board.removePiece(target);
				Position pawnPosition;
				if (p.getColor() == ColorPlayer.WHITE) {
					pawnPosition = new Position(3, target.getColumn());
				} else {
					pawnPosition = new Position(4, target.getColumn());
				}
				board.placePiece(pawn, pawnPosition);
				placeNewPiece('b', 2, new Pawn(board, ColorPlayer.WHITE, this));
			}
		}
	}

	private ChessPiece newPiece(String type,ColorPlayer color) {
		if (type.equals("B")) return new Bishop(board, color);
		if (type.equals("R")) return new Rook(board, color);
		if (type.equals("N")) return new Knight(board, color);
		return new Queen(board, color);
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
		this.setCurrentPlayer(this.getCurrentPlayer() == ColorPlayer.WHITE ? ColorPlayer.BLACK : ColorPlayer.WHITE);
	}

	private ColorPlayer opponent(ColorPlayer color) {
		return (color == ColorPlayer.WHITE ? ColorPlayer.BLACK : ColorPlayer.WHITE);
	}

	private ChessPiece king(ColorPlayer color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}

	private boolean testCheck(ColorPlayer color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testCheckMate(ColorPlayer color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	public void initialSetup() {
		// WHITE PIECES
		placeNewPiece('a', 2, new Pawn(board, ColorPlayer.WHITE, this));
		placeNewPiece('b', 2, new Pawn(board, ColorPlayer.WHITE, this));
		placeNewPiece('c', 2, new Pawn(board, ColorPlayer.WHITE, this));
		placeNewPiece('d', 2, new Pawn(board, ColorPlayer.WHITE, this));
		placeNewPiece('e', 2, new Pawn(board, ColorPlayer.WHITE, this));
		placeNewPiece('f', 2, new Pawn(board, ColorPlayer.WHITE, this));
		placeNewPiece('g', 2, new Pawn(board, ColorPlayer.WHITE, this));
		placeNewPiece('h', 2, new Pawn(board, ColorPlayer.WHITE, this));
		placeNewPiece('a', 1, new Rook(board, ColorPlayer.WHITE));
		placeNewPiece('b', 1, new Knight(board, ColorPlayer.WHITE));
		placeNewPiece('c', 1, new Bishop(board, ColorPlayer.WHITE));
		placeNewPiece('d', 1, new Queen(board, ColorPlayer.WHITE));
		placeNewPiece('e', 1, new King(board, ColorPlayer.WHITE, this));
		placeNewPiece('f', 1, new Bishop(board, ColorPlayer.WHITE));
		placeNewPiece('g', 1, new Knight(board, ColorPlayer.WHITE));
		placeNewPiece('h', 1, new Rook(board, ColorPlayer.WHITE));

		// BLACK PIECES
		placeNewPiece('a', 7, new Pawn(board, ColorPlayer.BLACK, this));
		placeNewPiece('b', 7, new Pawn(board, ColorPlayer.BLACK, this));
		placeNewPiece('c', 7, new Pawn(board, ColorPlayer.BLACK, this));
		placeNewPiece('d', 7, new Pawn(board, ColorPlayer.BLACK, this));
		placeNewPiece('e', 7, new Pawn(board, ColorPlayer.BLACK, this));
		placeNewPiece('f', 7, new Pawn(board, ColorPlayer.BLACK, this));
		placeNewPiece('g', 7, new Pawn(board, ColorPlayer.BLACK, this));
		placeNewPiece('h', 7, new Pawn(board, ColorPlayer.BLACK, this));
		placeNewPiece('a', 8, new Rook(board, ColorPlayer.BLACK));
		placeNewPiece('b', 8, new Knight(board, ColorPlayer.BLACK));
		placeNewPiece('c', 8, new Bishop(board, ColorPlayer.BLACK));
		placeNewPiece('d', 8, new Queen(board, ColorPlayer.BLACK));
		placeNewPiece('e', 8, new King(board, ColorPlayer.BLACK, this));
		placeNewPiece('f', 8, new Bishop(board, ColorPlayer.BLACK));
		placeNewPiece('g', 8, new Knight(board, ColorPlayer.BLACK));
		placeNewPiece('h', 8, new Rook(board, ColorPlayer.BLACK));
	}
}
