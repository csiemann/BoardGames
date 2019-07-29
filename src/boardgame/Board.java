package boardgame;

public class Board {
	private int rows;
	private int columns;
	private Piece[][] pieces;

	public Board(int rows, int columns) {
		if (rows < 1|| columns< 1) {
			throw new BoardException("Error creating board: there mus be at least 1 row and 1 column");
		}
		setRows(rows);
		setColumns(columns);
		this.pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	private void setRows(int rows) {
		this.rows = rows;
	}

	private void setColumns(int columns) {
		this.columns = columns;
	}

	public Piece piece(int row, int column) {
		if (!positionExists(row,column)) {
			throw new BoardException("Position not on the board");
		}
		return this.pieces[row][column];
	}

	public Piece piece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		return this.pieces[position.getRow()][position.getColumn()];
	}

	public void placePiece(Piece piece, Position position) {
		if (thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.setPosition(position);
	}

	private boolean positionExists(int row, int column) {
		return row >= 0 && row < this.rows && column >= 0 && column < this.columns;
	}

	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}

	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		return piece(position)!= null;
	}
}
