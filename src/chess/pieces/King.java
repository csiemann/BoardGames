package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != this.getColor();
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// above
		p.setValues(this.getPosition().getRow()-1, this.getPosition().getColumn());
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// below
		p.setValues(this.getPosition().getRow()+1, this.getPosition().getColumn());
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// left
		p.setValues(this.getPosition().getRow(), this.getPosition().getColumn()-1);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// right
		p.setValues(this.getPosition().getRow(), this.getPosition().getColumn()+1);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// above left
		p.setValues(this.getPosition().getRow()-1, this.getPosition().getColumn()-1);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// above right
		p.setValues(this.getPosition().getRow()-1, this.getPosition().getColumn()+1);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// below left
		p.setValues(this.getPosition().getRow()+1, this.getPosition().getColumn()-1);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// below right
		p.setValues(this.getPosition().getRow()+1, this.getPosition().getColumn()+1);
		if (this.getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		return mat;
	}

}
