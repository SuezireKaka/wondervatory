package www.wonder.vatory.framework.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class TimeEntity extends Entity {
	private Date regDt;
	private Date uptDt;

	public TimeEntity(String id) {
		super(id);
	}

	public void setCurDate() {
		if (regDt == null)
			regDt = new Date();
	}
	
	@Override
	public String toString() {
		return super.toString() + ", regDt=" + regDt + ", uptDt=" + uptDt;
	}
}
