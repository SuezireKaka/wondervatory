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
	private String name;
	String nick;
	String loginId;
	String passWord;
	String sex;
	Date birthDate;
	List<ContactPointVO> listContactPoint;
	//String introduction;
}
