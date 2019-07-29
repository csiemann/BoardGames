package chess;

import utils.AnsiEnum;

public enum Color {
	BLACK,
	WHITE;


	Color(){
	}

	public String format(ChessPiece piece, boolean background) {
		StringBuilder aux = new StringBuilder();
		int toggle = piece.getColor().ordinal()+(background?2:0);
		switch (toggle) {
		case 0:
			aux.append(AnsiEnum.print(piece, new AnsiEnum[] {AnsiEnum.BOLD,AnsiEnum.FOREGROUND_BLACK}));
			break;
		case 1:
			aux.append(AnsiEnum.print(piece, new AnsiEnum[] {AnsiEnum.BOLD,AnsiEnum.FOREGROUND_WHITE}));
			break;
		case 2:
			aux.append(AnsiEnum.print(piece, new AnsiEnum[] {AnsiEnum.BACKGROUND_YELLOW,AnsiEnum.BOLD,AnsiEnum.FOREGROUND_BLACK}));
			break;
		case 3:
			aux.append(AnsiEnum.print(piece, new AnsiEnum[] {AnsiEnum.BACKGROUND_YELLOW,AnsiEnum.BOLD,AnsiEnum.FOREGROUND_WHITE}));
			break;
		default:
			break;
		}
		return aux.toString();
	}

}
