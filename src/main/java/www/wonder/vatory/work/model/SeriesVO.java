package www.wonder.vatory.work.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SeriesVO extends ReplyVO  {
	String title;
	boolean isComplete;
	
	List<PostVO> postsList;
}
