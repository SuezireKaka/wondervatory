package www.wonder.vatory.tool.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.framework.model.TimeEntity;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ToolVO extends TimeEntity {
	String name;
	int xToolSize;
	int yToolSize;
	List<CustomEntityVO> customEntityList = new ArrayList<>();
	List<CustomRelationVO> customRelationList = new ArrayList<>();
}
