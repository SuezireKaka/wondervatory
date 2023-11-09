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
	public static final int UNIT_LENGTH = 4;
	
	//primitive type의 경우 기본값. reference type의 경우 null
	private String id;
	private int hTier;
	
	public void recurToParent() {
		this.id = id.substring(0, id.length() - 4);
		this.retier(0);
	}
	
	public String getParentId() {
		String myId = this.id;
		int len = myId.length();
		return myId.substring(0, len - UNIT_LENGTH);
	}
	
	public void retier(int control) {
		this.hTier = (this.id.length() - control) / 4;
	}
	
	@Override
	public String toString() {
		return "id=" + id;
	}
}
