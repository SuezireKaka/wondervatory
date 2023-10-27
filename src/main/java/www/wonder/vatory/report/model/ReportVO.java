package www.wonder.vatory.report.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.fileattachment.model.MappedTableDef;
import www.wonder.vatory.fileattachment.model.dto.AttachFileDTO;
import www.wonder.vatory.framework.model.TimeEntity;
import www.wonder.vatory.party.model.AccountVO;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ReportVO extends TimeEntity implements MappedTableDef{
	private AccountVO reporter;
	private String suspectId;
	private String suspectTable;
	private String cause;
	private List<ReportCodeVO> rptTypesList;
	private List<AttachFileDTO> listAttachFile;
	
	public void addRptType(ReportCodeVO rpt) {
		rptTypesList.add(rpt);
	}

	public String getMappedTableName() {
		return "T_report";
	}

	public String getKSuspectType() {
		return null;
	}

	@Override
	public List<AttachFileDTO> getListAttachFile() {
		return listAttachFile;
	}
}