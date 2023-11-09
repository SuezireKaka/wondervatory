package www.wonder.vatory.work.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.framework.property.ano.TargetProperty;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SemiPostVO extends ReplyVO {
	@TargetProperty
	private String title;
	private int readCount;
	private boolean isComplete;
	// 편법 - 이래놓고 세미포스트에만 쓰기
	private List<GenreVO> genreList;
	
	public void incReadCount() {
		readCount++;
	}
	private List<String> listTag;
}


