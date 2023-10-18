package www.wonder.vatory.tool.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PropertyRequestDTO {
	String propType;
	String propVal;
	String editType;
	int level;
}
