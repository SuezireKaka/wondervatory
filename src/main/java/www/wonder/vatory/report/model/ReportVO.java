package www.wonder.vatory.report.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.fileattachment.model.MappedTableDef;
import www.wonder.vatory.framework.model.TimeEntity;
import www.wonder.vatory.party.model.AccountVO;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ReportVO extends TimeEntity {
	AccountVO reporter;
	MappedTableDef suspect;
	String cause;
	List<ReportCodeVO> rptTypesList;
	
	public void addRptType(ReportCodeVO rpt) {
		rptTypesList.add(rpt);
	}
}