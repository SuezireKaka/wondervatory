package www.wonder.vatory.fileattachment.model;

import java.util.List;

import www.wonder.vatory.fileattachment.model.dto.AttachFileDTO;

public interface MappedTableDef {
	public String getMappedTableName();
	public String getId();
	public List<AttachFileDTO> getListAttachFile();
}
