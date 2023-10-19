package www.wonder.vatory.tool.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CustomPropertyDTO {
	private String propType;
	private String propVal;
	private boolean isEdited;
}
