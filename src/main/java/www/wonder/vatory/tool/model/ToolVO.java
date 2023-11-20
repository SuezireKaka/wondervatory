package www.wonder.vatory.tool.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.fileattachment.model.MappedTableDef;
import www.wonder.vatory.fileattachment.model.dto.AttachFileDTO;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.work.model.SeriesVO;
import www.wonder.vatory.work.model.WorkVO;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ToolVO extends WorkVO implements MappedTableDef {
	private String name;
	private int xToolSize;
	private int yToolSize;
	private SeriesVO series;
	private AccountVO writer;
	private List<CustomEntityVO> customEntityList = new ArrayList<>();
	private List<CustomRelationVO> customRelationList = new ArrayList<>();
	private List<AttachFileDTO> listAttachFile;
	
	public String getMappedTableName() {
		return "t_tool";
	}
	
	public String getKSuspectType() {
		return "툴";
	}
	
	public List<AttachFileDTO> getListAttachFile() {
		return listAttachFile;
	}
}
