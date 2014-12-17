public class Main {
	public static void main(String[] args) {
		String s = "abcdefghijklmnopqrstuvxyzåäö";
		char[] c = s.toCharArray();
		int i;
		for (char ch : c) {
			i = ch-'a';
			System.out.println(i);
		}
		for (int j = 0; j < 30; j++) {
			char cha = 0;
			System.out.println('a'+cha+j);
		}
	}
}
