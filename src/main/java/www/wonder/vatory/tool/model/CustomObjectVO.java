package www.wonder.vatory.tool.model;

import java.util.ArrayList;
import java.util.List;

import www.wonder.vatory.framework.model.SpaceEntity;

public class CustomObjectVO extends SpaceEntity {
	String name;
	
	List<CustomPropertyVO> customPropertiesList = new ArrayList<>();
}
