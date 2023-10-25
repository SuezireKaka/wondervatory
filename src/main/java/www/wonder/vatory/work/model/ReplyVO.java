package www.wonder.vatory.work.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.fileattachment.model.MappedTableDef;
import www.wonder.vatory.fileattachment.model.dto.AttachFileDTO;
import www.wonder.vatory.framework.model.TimeEntity;
import www.wonder.vatory.framework.property.ano.TargetProperty;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.report.model.ReportVO;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ReplyVO extends TimeEntity implements MappedTableDef {
	public String getMappedTableName() {
		return "T_work";
	}
	
	private BoardVO boardVO;
	@TargetProperty
	private AccountVO writer;	//게시물 작성자
	private int hTier;
	private String content;	//내용, series에서는 줄거리
	private int readCount;
	private int likeCount; //series는 post 총합
	private int dislikeCount; //series는 post 총합
	
	private boolean favorites;
	// compose pattern
	private List<ReplyVO> repliesList = new ArrayList<>();
	private List<String> listTag;
	private List<AttachFileDTO> listAttachFile;
	private List<ReportVO> reportsList;
	
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
	
	public void incReadCount() {
		readCount++;
	}
	
	public void incLikeCount() {
		likeCount++;
	}
	
	public String getKSuspectType() {
		int idLength = this.getId().length();
		switch (idLength) {
			case 4 : return "시리즈";
			case 8 : return "포스트";
			default : return "댓글";
		}
		
	}

}
