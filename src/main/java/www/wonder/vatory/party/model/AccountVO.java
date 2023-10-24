package www.wonder.vatory.party.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import www.wonder.vatory.fileattachment.model.MappedTableDef;
import www.wonder.vatory.fileattachment.model.dto.AttachFileDTO;
import www.wonder.vatory.framework.model.TimeEntity;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountVO extends TimeEntity implements UserDetails, MappedTableDef {
	public String getMappedTableName() {
		return "T_account";
	}
	
	private String loginId;
	private String passWord;
	private OrganizationVO owner;	//주인으로서
	private PersonVO response;	//대상으로서
	private String nick; // 화면에 표시되는 필명 - 중복검사할거임!
	private String introduction;
	private boolean isAlive;
	private Collection<RoleVO> roleList;
	private AttachFileDTO profileImage;
	
	public void encodePswd(PasswordEncoder pswdEnc) {
		passWord = pswdEnc.encode(passWord);
	}

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

	@Override
	public List<AttachFileDTO> getListAttachFile() {
		List<AttachFileDTO> result = new ArrayList<>();
		result.add(profileImage);
		return result;
	}

}
