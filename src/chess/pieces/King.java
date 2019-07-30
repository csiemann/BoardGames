package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	private ChessMatch chessMatch;

	public King(Board board, Color color,ChessMatch chessMatch) {
		super(board, color);
		setChessMatch(chessMatch);
	}

	public ChessMatch getChessMatch() {
		return chessMatch;
	}

	private void setChessMatch(ChessMatch chessMatch) {
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != this.getColor();
	}

	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == this.getColor() && p.getMoveCount() == 0;
	}


	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// above
		p.setValues(this.getPosition().getRow() - 1, this.getPosition().getColumn());
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// below
		p.setValues(this.getPosition().getRow() + 1, this.getPosition().getColumn());
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// left
		p.setValues(this.getPosition().getRow(), this.getPosition().getColumn() - 1);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// right
		p.setValues(this.getPosition().getRow(), this.getPosition().getColumn() + 1);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// above left
		p.setValues(this.getPosition().getRow() - 1, this.getPosition().getColumn() - 1);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// above right
		p.setValues(this.getPosition().getRow() - 1, this.getPosition().getColumn() + 1);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// below left
		p.setValues(this.getPosition().getRow() + 1, this.getPosition().getColumn() - 1);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// below right
		p.setValues(this.getPosition().getRow() + 1, this.getPosition().getColumn() + 1);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// #specialmove castling
		if (getMoveCount() == 0 && !chessMatch.isCheck()) {
			// #specialmove castling kingside rook
			Position posR1 = new Position(this.getPosition().getRow(), this.getPosition().getColumn() + 3);
			if (testRookCastling(posR1)) {
				Position p1 = new Position(this.getPosition().getRow(), this.getPosition().getColumn() + 1);
				Position p2 = new Position(this.getPosition().getRow(), this.getPosition().getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat[this.getPosition().getRow()][this.getPosition().getColumn() + 2] = true;
				}
			}
			// #specialmove castling queenside rook
			Position posR2 = new Position(this.getPosition().getRow(), this.getPosition().getColumn() - 4);
			if (testRookCastling(posR2)) {
				Position p1 = new Position(this.getPosition().getRow(), this.getPosition().getColumn() - 1);
				Position p2 = new Position(this.getPosition().getRow(), this.getPosition().getColumn() - 2);
				Position p3 = new Position(this.getPosition().getRow(), this.getPosition().getColumn() - 3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[this.getPosition().getRow()][this.getPosition().getColumn() - 2] = true;
				}
			}
		}

		return mat;
	}

}
