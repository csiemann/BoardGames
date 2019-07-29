package tests;

public class AnsiTest {

	public static void main(String[] args) {
		for (int i = 0; i < 8; i++) {
			System.out.print(" \033["+"1;"+(30+i)+"mteste"+"\033[0m ");
		}
		System.out.println();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(" \033["+(30+i)+";1;"+(j+40)+"mteste"+"\033[0m ");
			}
			System.out.println();
		}
	}

}
