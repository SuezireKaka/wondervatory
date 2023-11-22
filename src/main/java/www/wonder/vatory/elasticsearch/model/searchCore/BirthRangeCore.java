package www.wonder.vatory.elasticsearch.model.searchCore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BirthRangeCore extends RangeCore {
	private TimeRange birth;
}
