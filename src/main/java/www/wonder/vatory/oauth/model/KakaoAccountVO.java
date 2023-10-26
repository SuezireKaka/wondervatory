package www.wonder.vatory.oauth.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import www.wonder.vatory.party.model.PersonVO;
import www.wonder.vatory.party.model.RoleVO;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoAccountVO {
	public static final String ACCOUNT_TYPE = "카카오";
	
    private Long userCode; 
    private Long kakaoId;
    private String kakaoProfileImg;
    private String kakaoNick;
    private PersonVO response;
    private Date regDt;

    private List<RoleVO> roleList;
    
    public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.getRoleList()
				.stream()
				.map(RoleVO::getAuthority)
				.collect(Collectors.toList());
	}
}