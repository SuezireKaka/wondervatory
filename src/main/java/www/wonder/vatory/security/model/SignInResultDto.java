package www.wonder.vatory.security.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 로그인 결과 정보 
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignInResultDto extends SignUpResultDto {
	private String token;	//JWT
	private String userId;
	private String userLoginId;
	private String userNick;
	private String type; // wonder? kakao?
	private Integer loginResultCode;
	private List<String> roles;
	
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