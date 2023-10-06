package www.wonder.vatory.party.model;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RoleVO {
	String role;
	String manual;
	List<ActVO> allowedActsList;
	
	public SimpleGrantedAuthority getAuthority() {
		return new SimpleGrantedAuthority(role);
	}
}
