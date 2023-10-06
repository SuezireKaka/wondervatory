package www.wonder.vatory.framework.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class Entity {
	/** t_work의 id 단위 길이 */
	public static final int ID_LENGTH = 4;
	
	//primitive type의 경우 기본값. reference type의 경우 null
	private String id;
	
	@Override
	public String toString() {
		return "id=" + id;
	}
}
