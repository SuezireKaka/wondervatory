package www.wonder.vatory.elasticsearch.model.searchInput.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.elasticsearch.model.searchInput.searchCore.RangeCore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RangeCondition extends Condition {
	private RangeCore range;
}
