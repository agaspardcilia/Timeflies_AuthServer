import java.util.Map;

public class Test {
	public static void main(String[] args) {
		Map<String, String> m =System.getenv();
		
		for (String s : m.keySet()) {
			System.out.println(s +" -> "+ m.get(s));
		}
	}
}
