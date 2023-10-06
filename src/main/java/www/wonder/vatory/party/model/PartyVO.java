package www.wonder.vatory.party.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import www.wonder.vatory.framework.model.TimeEntity;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PartyVO extends TimeEntity {
	private String accountType;
	private String ownerId;	//주인으로서
	private String responsId;	//대상으로서
	private boolean alive;
	
	String name;
	Date BirthDate; // 회사의 경우 설립일
	List<ContactPointVO> contactPointList;
	List<AccountVO> accountList;
	
	public void addCP(ContactPointVO cp) {
		contactPointList.add(cp);
	}

	public void addAccount(AccountVO o) {
		accountList.add(o);
	}
}
