package www.wonder.vatory.tool.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ToolSkinDTO {
	ToolVO newTool;
	String parentId;
	String parentType;
}
