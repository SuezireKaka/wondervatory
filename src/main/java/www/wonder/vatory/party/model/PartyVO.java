package www.wonder.vatory.party.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import www.wonder.vatory.framework.model.Entity;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PartyVO extends Entity {	
	String name;
	Date birthDate; // 회사의 경우 설립일
	List<ContactPointVO> contactPointList;
	
	public void addCP(ContactPointVO cp) {
		contactPointList.add(cp);
	}
}
