package www.wonder.vatory.party.model;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import www.wonder.vatory.framework.model.TimeEntity;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value={"passWord"}, allowSetters=true) //pswd는 화면에 노출하는 대상이 아님! 보안!
public class AccountVO extends TimeEntity implements UserDetails {
	private OrganizationVO owner;	//주인으로서
	private PersonVO response;	//대상으로서
	private boolean alive;
	private String loginId;
	private String passWord;
	private String nick; // 화면에 표시되는 필명 - 중복검사할거임!
	private String introduction;
	private boolean isAlive;
	private Collection<RoleVO> roleList;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roleList
				.stream()
				.map(RoleVO::getAuthority)
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return passWord;
	}

	@Override
	public String getUsername() {
		return nick;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAlive;
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
