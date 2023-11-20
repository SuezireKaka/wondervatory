package www.wonder.vatory.work.model;

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
public class WorkVO extends TimeEntity {
	
	@TargetProperty
	private AccountVO writer;	//게시물 작성자
	private boolean alive;
	
	public WorkVO(String id) {
		super.setId(id);
	};

}

