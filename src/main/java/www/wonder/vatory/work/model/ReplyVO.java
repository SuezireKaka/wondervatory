package www.wonder.vatory.work.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.framework.model.TimeEntity;
import www.wonder.vatory.framework.property.ano.TargetProperty;
import www.wonder.vatory.party.model.AccountVO;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ReplyVO extends TimeEntity {

	private BoardVO boardVO;
	@TargetProperty
	private AccountVO writer;	//게시물 작성자
	private int hTier;
	private String content;	//내용, series에서는 줄거리
	private int readCount;
	private int likeCount; //series는 post 총합
	private int dislikeCount; //series는 post 총합
	// compose pattern
	private List<ReplyVO> repliesList = new ArrayList<>();
	
	public ReplyVO(String id) {
		super.setId(id);
	}
	
	public String getParentId() {
		String myId = super.getId();
		int len = myId.length();
		return myId.substring(0, len - ID_LENGTH);
	}

	public void appendReply(ReplyVO reply) {
		repliesList.add(reply);
	}
}
