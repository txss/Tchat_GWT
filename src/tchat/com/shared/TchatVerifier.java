package tchat.com.shared;

public class TchatVerifier {

	public static boolean isValidNickname(String nname) {
		return nname.matches("[a-zA-Z]{2,8}?");
	}
}
