package www.wonder.vatory.party.model;

import java.util.Date;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SignUpDto {
	String loginId;
	String passWord;
	String nick;
	String name;
	String sex;
	Date birthDate;
	String introduction;
}
