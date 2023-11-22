package www.wonder.vatory.elasticsearch.model.searchInput.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.elasticsearch.model.searchInput.searchCore.RegExpCore;
import www.wonder.vatory.elasticsearch.model.searchInput.shell.Bool;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegExpCondition extends Condition {
	private RegExpCore regexp;
}
