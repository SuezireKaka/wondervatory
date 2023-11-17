package www.wonder.vatory.tool.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.work.model.SeriesVO;
import www.wonder.vatory.work.model.WorkVO;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ToolVO extends WorkVO {
	private String name;
	private int xToolSize;
	private int yToolSize;
	private SeriesVO series;
	private AccountVO writer;
	private List<CustomEntityVO> customEntityList = new ArrayList<>();
	private List<CustomRelationVO> customRelationList = new ArrayList<>();
}
