package www.wonder.vatory.elasticsearch.model.shell;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.elasticsearch.model.condition.Condition;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bool {
	private List<Condition> must;
	
	
}
