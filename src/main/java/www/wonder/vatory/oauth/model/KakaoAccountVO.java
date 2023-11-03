package www.wonder.vatory.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import www.wonder.vatory.party.model.AccountVO;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoAccountVO extends AccountVO {
	public static final String ACCOUNT_TYPE = "카카오";
	
	private long kakaoId;
	private String kakaoNick;
	
	public String getAccountType() {
		return ACCOUNT_TYPE;
	}

}