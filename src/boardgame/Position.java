package boardgame;

public class Position {

	private int row;
	private int column;

	//Constructor

	public Position(int row,int column) {
		setRow(row);
		setColumn(column);
	}

	//Getters

	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}

	//Setters

	public void setRow(int row) {
		this.row = row;
	}
	public void setColumn(int column) {
		this.column = column;
	}

	@Override
	public String toString() {
		return getRow()+", "+getColumn();
	}
}
