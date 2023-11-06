package www.wonder.vatory.tool.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.framework.model.SpaceEntity;

@Getter
@Setter
@NoArgsConstructor
public class CustomObjectVO extends SpaceEntity {
	String name;
	
	List<CustomPropertyVO> customPropertiesList = new ArrayList<>();
}
