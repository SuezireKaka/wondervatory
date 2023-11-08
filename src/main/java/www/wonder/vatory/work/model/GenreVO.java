package www.wonder.vatory.work.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.fileattachment.model.MappedTableDef;
import www.wonder.vatory.fileattachment.model.dto.AttachFileDTO;
import www.wonder.vatory.framework.model.Entity;
import www.wonder.vatory.report.model.ReportCodeVO;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)	
public class GenreVO extends Entity {
	String genre;
	String info;
}
