package www.wonder.vatory.elasticsearch.model.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.elasticsearch.model.searchCore.RegExpCore;
import www.wonder.vatory.elasticsearch.model.shell.Bool;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegExpCondition extends Condition {
	private RegExpCore regexp;
}
