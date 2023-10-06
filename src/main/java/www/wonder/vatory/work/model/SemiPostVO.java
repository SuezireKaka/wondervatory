package www.wonder.vatory.work.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SemiPostVO extends ReplyVO {
	private String title;
	//private int readCount; ReplyVO에 있어서 지울예정
	private boolean isComplete;
}
