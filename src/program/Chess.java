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
        ChessMatch match = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<ChessPiece>();
        while (true) {
            try {
                UIChess.cleanScreen();
                UIChess.printMatch(match,captured);

                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UIChess.readChessPosition(scanner);

                boolean[][] possibleMoves = match.possibleMoves(source);
                UIChess.cleanScreen();
                UIChess.printBoard(match.getPieces(),possibleMoves);

                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UIChess.readChessPosition(scanner);

                ChessPiece capturedPiece = match.performChessMove(source, target);

                if (capturedPiece != null) {
                	captured.add(capturedPiece);
                }

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
