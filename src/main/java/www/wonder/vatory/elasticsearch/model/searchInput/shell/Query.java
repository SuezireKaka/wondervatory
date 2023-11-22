package www.wonder.vatory.elasticsearch.model.searchInput.shell;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Query {
	private Bool bool;
}
