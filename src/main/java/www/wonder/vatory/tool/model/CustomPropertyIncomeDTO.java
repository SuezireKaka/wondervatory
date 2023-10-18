package www.wonder.vatory.tool.model;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CustomPropertyIncomeDTO {
	String objectId;
	List<CustomPropertyVO> customPropertyList;
}
