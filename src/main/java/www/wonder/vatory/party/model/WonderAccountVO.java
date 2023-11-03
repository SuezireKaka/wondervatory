package www.wonder.vatory.party.model;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WonderAccountVO extends AccountVO implements UserDetails {
	public static final String ACCOUNT_TYPE = "원더";
	
	private String loginId;
	private String passWord;
	
	private String nick; // 화면에 표시되는 필명 - 원더는 중복검사할거임!

	private String introduction;
	
	public String getMappedTableName() {
		return "T_account";
	}
	
	public void encodePswd(PasswordEncoder pswdEnc) {
		passWord = pswdEnc.encode(passWord);
	}

	@Override
	public String getPassword() {
		return passWord;
	}

	@Override
	public String getUsername() {
		return this.getNick();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
