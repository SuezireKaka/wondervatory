package www.wonder.vatory.party.model;

import java.util.List;

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
	// 다중계정으로의 확장가능성을 잃지 않은 채로 단일계정만 허용하기 - 원더 어카운트 기준
	public static final int MAX_WONDER_ACCOUNT_NUMBER = 1;
	
	private String sex; // Male or Female
	//private List<AccountVO> accountList;
	/*
	public void addAccount(AccountVO acc) {
		accountList.add(acc);
	}
	*/
}
