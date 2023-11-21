package www.wonder.vatory.elasticsearch.model.searchCore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeRange {
	private String gte;
	private String lt;
}
