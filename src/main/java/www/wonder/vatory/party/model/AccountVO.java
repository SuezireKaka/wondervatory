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
import www.wonder.vatory.framework.property.ano.TargetProperty;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountVO extends TimeEntity implements MappedTableDef {

	private OrganizationVO owner;	//주인으로서
	private PersonVO response;	//대상으로서
	
	private int loginResultCode; // 문제없음 : 1, 탈퇴계정 : 2, 만료계정 : 3, 처벌계정 : 4
	private Collection<RoleVO> roleList;
	private AttachFileDTO profileImage;
	
	public String getMappedTableName() {
		return "T_account";
	}

	@Override
	public List<AttachFileDTO> getListAttachFile() {
		List<AttachFileDTO> result = new ArrayList<>();
		result.add(profileImage);
		return result;
	}

	public String getKSuspectType() {
		return "사용자";
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.getRoleList()
				.stream()
				.map(RoleVO::getAuthority)
				.collect(Collectors.toList());
	}

}
