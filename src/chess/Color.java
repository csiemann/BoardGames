package chess;

public enum Color {
	BLACK("\033[40;1;37m"),
	WHITE("\033[47;1;30m");

	private String color;

	Color(String color){
		this.color = color;
	}

	public String format(ChessPiece piece) {
		return color+piece+"\033[0m";
	}

}
