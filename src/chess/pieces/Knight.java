package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.ColorPlayer;

public class Knight extends ChessPiece {

	public Knight(Board board, ColorPlayer color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "N";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != this.getColor();
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// above left
		p.setValues(this.getPosition().getRow() - 2, this.getPosition().getColumn() - 1);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// above right
		p.setValues(this.getPosition().getRow() - 2, this.getPosition().getColumn() + 1);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// below left
		p.setValues(this.getPosition().getRow() + 2, this.getPosition().getColumn() - 1);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// right below
		p.setValues(this.getPosition().getRow() + 2, this.getPosition().getColumn() + 1);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// left above
		p.setValues(this.getPosition().getRow() - 1, this.getPosition().getColumn() - 2);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// below left
		p.setValues(this.getPosition().getRow() + 1, this.getPosition().getColumn() - 2);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// right above
		p.setValues(this.getPosition().getRow() - 1, this.getPosition().getColumn() + 2);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// right below
		p.setValues(this.getPosition().getRow() + 1, this.getPosition().getColumn() + 2);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		return mat;
	}

}
