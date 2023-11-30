package www.wonder.vatory.framework.model.interfaces;

import java.util.List;

import www.wonder.vatory.fileattachment.model.dto.AttachFileDTO;
import www.wonder.vatory.report.model.ReportVO;

public interface MappedTableDef {
	public String getMappedTableName();
	public String getId();
	public String getKSuspectType();
	public List<AttachFileDTO> getListAttachFile();
}
