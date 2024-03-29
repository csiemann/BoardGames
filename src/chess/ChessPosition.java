package chess;

import boardgame.Position;

public class ChessPosition {
	private char column;
	private int row;

	public ChessPosition(char column,int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8");
		}
		setColumn(column);
		setRow(row);
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	private void setColumn(char column) {
		this.column = column;
	}

	private void setRow(int row) {
		this.row = row;
	}

	protected Position toPosition() {
		return new Position(8 - getRow(), getColumn() - 'a');
	}

	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
	}

	@Override
	public String toString() {
		return ""+getColumn()+getRow();
	}
}
