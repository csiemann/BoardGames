package program;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.UIChessTerminal;

public class ChessTerminal {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<ChessPiece>();
		while (!chessMatch.isCheckMate()) {
			try {
				UIChessTerminal.cleanScreen();
				UIChessTerminal.printMatch(chessMatch, captured);

				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UIChessTerminal.readChessPosition(scanner);

				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UIChessTerminal.cleanScreen();
				UIChessTerminal.printBoard(chessMatch.getPieces(), possibleMoves);

				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UIChessTerminal.readChessPosition(scanner);

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
		UIChessTerminal.cleanScreen();
		UIChessTerminal.printMatch(chessMatch, captured);
	}
}
