package www.wonder.vatory.tool.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.framework.model.TimeEntity;
import www.wonder.vatory.work.model.SeriesVO;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ToolVO extends TimeEntity {
	int xToolSize;
	int yToolSize;
	String name;
	SeriesVO parentSeries;
	CustomEntityVO parentEntity;
}
