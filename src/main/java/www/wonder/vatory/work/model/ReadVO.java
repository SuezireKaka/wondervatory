package www.wonder.vatory.work.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.party.model.AccountVO;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ReadVO {
	AccountVO reader;
	SemiPostVO readee;
	Date time;
}
