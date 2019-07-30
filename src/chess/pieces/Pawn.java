package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		setChessMatch(chessMatch);
	}

	private void setChessMatch(ChessMatch chessMatch) {
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// Pawn white
		if (getColor() == Color.WHITE) {
			p.setValues(getPosition().getRow() - 1, getPosition().getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(getPosition().getRow() - 2, getPosition().getColumn());
			Position p2 = new Position(getPosition().getRow() - 1, getPosition().getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2)
					&& !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(getPosition().getRow() - 1, getPosition().getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(getPosition().getRow() - 1, getPosition().getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// #specialmove en passant white
			if (this.getPosition().getRow() == 3) {
				Position left = new Position(this.getPosition().getRow(), this.getPosition().getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
						&& getBoard().piece(left) == chessMatch.getEnPassantVunerable()) {
					mat[left.getRow()-1][left.getColumn()] = true;
				}
				Position right = new Position(this.getPosition().getRow(), this.getPosition().getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
						&& getBoard().piece(right) == chessMatch.getEnPassantVunerable()) {
					mat[right.getRow()-1][right.getColumn()] = true;
				}
			}
		} else {
			// Pawn black
			p.setValues(getPosition().getRow() + 1, getPosition().getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(getPosition().getRow() + 2, getPosition().getColumn());
			Position p2 = new Position(getPosition().getRow() + 1, getPosition().getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2)
					&& !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(getPosition().getRow() + 1, getPosition().getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(getPosition().getRow() + 1, getPosition().getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// #specialmove en passant black
						if (this.getPosition().getRow() == 4) {
							Position left = new Position(this.getPosition().getRow(), this.getPosition().getColumn() - 1);
							if (getBoard().positionExists(left) && isThereOpponentPiece(left)
									&& getBoard().piece(left) == chessMatch.getEnPassantVunerable()) {
								mat[left.getRow()+1][left.getColumn()] = true;
							}
							Position right = new Position(this.getPosition().getRow(), this.getPosition().getColumn() + 1);
							if (getBoard().positionExists(right) && isThereOpponentPiece(right)
									&& getBoard().piece(right) == chessMatch.getEnPassantVunerable()) {
								mat[right.getRow()+1][right.getColumn()] = true;
							}
						}
		}

		return mat;
	}
}
