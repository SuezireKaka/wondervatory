package www.wonder.vatory.security.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 로그인 결과 정보 
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignInResultDto extends SignUpResultDto {
	private String token;	//JWT
	private String userId;
	private String userLoginId;
	private String userNick;
	private List<String> roles;
	
	
	@Builder
	public SignInResultDto(boolean success, int code, 
			String msg, String token, List<String> roles, String userId, String userLoginId, String userNick) {
		super(success, code, msg);
		this.token = token;
		this.roles = roles;
		this.userId = userId;
		this.userLoginId = userLoginId;
		this.userNick = userNick;
	}

}