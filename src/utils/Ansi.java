package utils;

public class Ansi {

	public Ansi() {}

	public static String print(Object msg , AnsiEnum[] styles) {
		StringBuilder sb = new StringBuilder();
		sb.append(AnsiEnum.INIT);
		for (int i = 0; i < styles.length; i++) {
			sb.append(styles[i]);
			if (i != styles.length-1) {
				sb.append(AnsiEnum.SEPARATOR);
			}
		}
		sb.append(AnsiEnum.FINAL);
		sb.append(msg);
		sb.append(AnsiEnum.RESET);
		return sb.toString();
	}


}
