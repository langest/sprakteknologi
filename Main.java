public class Main {
	public static void main(String[] args) {
		String s = "abcdefghijklmnopqrstuvxyzåäö";
		char[] c = s.toCharArray();
		int i;
		for (char ch : c) {
			i = ch-'a';
			System.out.println(i);
		}
	}
}
