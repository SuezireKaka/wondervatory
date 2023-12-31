package www.wonder.vatory.tool.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CustomPropertyVO {	
	private String propType;
	private String propVal;
	private boolean isEdited;
	private boolean isSafe = true;

	@Override
	public String toString() {
		return "[propType=" + propType + ", propVal=" + propVal + "]";
	}
}
