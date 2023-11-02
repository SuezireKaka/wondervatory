package www.wonder.vatory.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import www.wonder.vatory.party.model.AccountVO;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoAccountVO extends AccountVO {
	public static final String ACCOUNT_TYPE = "카카오";
	
	private Long kakaoId;
	private String kakaoNick;
    
    @Override
	public String getAccountType() {
		return ACCOUNT_TYPE;
	}

}