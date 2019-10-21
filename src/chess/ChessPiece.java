package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private ColorPlayer color;
	private int moveCount;

	public ChessPiece(Board board, ColorPlayer color) {
		super(board);
		this.color = color;
	}

	public ColorPlayer getColor() {
		return this.color;
	}

	public int getMoveCount() {
		return moveCount;
	}

	public void increaseMoveCount() {
		this.moveCount++;
	}

	public void decreaseMoveCount() {
		this.moveCount--;
	}

	protected ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(super.getPosition());
	}

	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p.getColor() != this.getColor();
	}
}
