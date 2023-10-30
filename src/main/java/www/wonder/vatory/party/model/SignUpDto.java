package www.wonder.vatory.party.model;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignUpDto {
	private String accountId;
	private String partyId;
	private String name;
	private String nick;
	private String loginId;
	private String passWord;
	private String sex;
	private Date birthDate;
	private List<ContactPointVO> listContactPoint;
	//String introduction;
}
