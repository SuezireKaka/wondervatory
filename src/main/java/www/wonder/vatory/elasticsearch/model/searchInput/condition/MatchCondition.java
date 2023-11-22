package www.wonder.vatory.elasticsearch.model.searchInput.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.elasticsearch.model.searchInput.searchCore.MatchCore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchCondition extends Condition {
	private MatchCore match;
}
