package skycard;


import java.util.Scanner;

class skyCastMain {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		SkyCast obj = new SkyCast();
		String range = in.nextLine();
		String skipList = in.nextLine();
		String favourite = in.nextLine();
		System.out.println(obj.clickCount(range, skipList, favourite));
		in.close();

	}
}

