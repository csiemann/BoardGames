package utils;

public enum AnsiEnum {
	RESET("\033[0m"),
	INIT("\033["),
	SEPARATOR(";"),
	FINAL("m"),
	BOLD("1"),
	FOREGROUND_BLACK("30"),
	FOREGROUND_RED("31"),
	FOREGROUND_GREEN("32"),
	FOREGROUND_YELLOW("33"),
	FOREGROUND_BLUE("34"),
	FOREGROUND_PURPLE("35"),
	FOREGROUND_CYAN ("36"),
	FOREGROUND_WHITE ("37"),
	BACKGROUND_BLACK("40"),
	BACKGROUND_RED("41"),
	BACKGROUND_GREEN("42"),
	BACKGROUND_YELLOW("43"),
	BACKGROUND_BLUE("44"),
	BACKGROUND_PURPLE("45"),
	BACKGROUND_CYAN ("46"),
	BACKGROUND_WHITE("47");

	private String ansi;

	AnsiEnum(String ansi){
		this.ansi = ansi;
	}

	public static String print(Object msg , AnsiEnum[] styles) {
		StringBuilder sb = new StringBuilder();
		sb.append(INIT);
		for (int i = 0; i < styles.length; i++) {
			sb.append(styles[i]);
			if (i != styles.length-1) {
				sb.append(SEPARATOR);
			}
		}
		sb.append(FINAL);
		sb.append(msg);
		sb.append(RESET);
		return sb.toString();
	}

	public static String color(AnsiEnum[] styles) {
		StringBuilder sb = new StringBuilder();
		sb.append(INIT);
		for (int i = 0; i < styles.length; i++) {
			sb.append(styles[i]);
			if (i != styles.length-1) {
				sb.append(SEPARATOR);
			}
		}
		sb.append(FINAL);
		return sb.toString();
	}

	@Override
	public String toString() {
		return ansi;
	}
}
