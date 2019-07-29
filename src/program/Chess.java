package program;

import chess.ChessMatch;
import chess.UIChess;

public class Chess {
	public static void main(String[] args) {
		ChessMatch match = new ChessMatch();
		UIChess.printBoard(match.getPieces());
	}
}
