package www.wonder.vatory.security.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EmergencysPasswordEncode {

	public static void main(String[] args) {
		PasswordEncoder pswdEn = new BCryptPasswordEncoder();
		
		String rawPassWord = "aaaa";
		
		System.out.println(pswdEn.encode(rawPassWord));

	}

}
