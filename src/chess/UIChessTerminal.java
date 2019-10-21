package chess;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import utils.AnsiEnum;

public class UIChessTerminal {

	public static void cleanScreen() {
		System.out.println("\033[H\033[2J");
		System.out.flush();
	}

	public static ChessPosition readChessPosition(Scanner scanner) {
		try {
			String s = scanner.nextLine();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1));
			return new ChessPosition(column, row);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Error instantiating ChessPosition. Valid values are from a1 to h8");
		}
	}

	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		printBoard(chessMatch.getPieces());
		System.out.println();
		printCapturePieces(captured);
		System.out.println();
		System.out.println("Turn : " + chessMatch.getTurn());
		if (!chessMatch.isCheckMate()) {
			System.out.println("Wainting player : " + chessMatch.getCurrentPlayer());
			if (chessMatch.isCheck()) {
				System.out.println("CHECK!");
			}
		} else {
			System.out.println("CHECKMATE!");
			System.out.println("WINNER: " + chessMatch.getCurrentPlayer());
		}
	}

	public static void printBoard(ChessPiece[][] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	private static void printPiece(ChessPiece piece, boolean background) {
		if (background) {
			System.out.print(AnsiEnum.color(new AnsiEnum[] { AnsiEnum.BACKGROUND_YELLOW }));
		}
		if (piece == null) {
			System.out.print("-" + AnsiEnum.RESET);
		} else {
			System.out.print(piece.getColor().format(piece, background));
		}
		System.out.print(" ");
	}

	private static void printCapturePieces(List<ChessPiece> captured) {
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == ColorPlayer.WHITE)
				.collect(Collectors.toList());
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == ColorPlayer.BLACK)
				.collect(Collectors.toList());
		System.out.println("Captured pieces:");
		System.out.print("White: ");
		System.out.println(AnsiEnum.print(Arrays.toString(white.toArray()),
				new AnsiEnum[] { AnsiEnum.BACKGROUND_CYAN, AnsiEnum.BOLD, AnsiEnum.FOREGROUND_WHITE }));
		System.out.print("Black: ");
		System.out.println(AnsiEnum.print(Arrays.toString(black.toArray()),
				new AnsiEnum[] { AnsiEnum.BACKGROUND_YELLOW, AnsiEnum.BOLD, AnsiEnum.FOREGROUND_BLACK }));
	}
}
