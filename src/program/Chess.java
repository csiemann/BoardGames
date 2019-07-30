package program;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.UIChess;

public class Chess {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<ChessPiece>();
		while (!chessMatch.isCheckMate()) {
			try {
				UIChess.cleanScreen();
				UIChess.printMatch(chessMatch, captured);

				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UIChess.readChessPosition(scanner);

				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UIChess.cleanScreen();
				UIChess.printBoard(chessMatch.getPieces(), possibleMoves);

				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UIChess.readChessPosition(scanner);

				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

				if (capturedPiece != null) {
					captured.add(capturedPiece);
				}

				if (chessMatch.getPromoted() != null) {
					System.out.print("Enter piece for promotion (B/N/R/Q): ");
					String type = scanner.nextLine();
					chessMatch.replacePromotedPiece(type);
				}

			} catch (ChessException e) {
				System.out.println(e.getMessage());
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				scanner.nextLine();
			}
		}
		UIChess.cleanScreen();
		UIChess.printMatch(chessMatch, captured);
	}
}
