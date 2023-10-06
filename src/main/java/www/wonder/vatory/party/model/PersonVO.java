package www.wonder.vatory.party.model;

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
public class PersonVO extends PartyVO {
	// 다중계정으로의 확장가능성을 잃지 않은 채로 단일계정만 허용하기
	public static final int MAX_ACCOUNT_NUMBER = 1;
	
	private String sex; // Male or Female
}
