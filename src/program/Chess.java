package program;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.UIChess;

public class Chess {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ChessMatch match = new ChessMatch();
		while (true) {
			try {
				UIChess.cleanScreen();
				UIChess.printBoard(match.getPieces());

				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UIChess.readChessPosition(scanner);

				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UIChess.readChessPosition(scanner);

				ChessPiece capturedPiece = match.performChessMove(source, target);

			} catch (ChessException e) {
				System.out.println(e.getMessage());
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				scanner.nextLine();
			}
		}
	}
}
