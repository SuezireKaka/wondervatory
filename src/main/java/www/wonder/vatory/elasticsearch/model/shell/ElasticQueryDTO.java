package www.wonder.vatory.elasticsearch.model.shell;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElasticQueryDTO {
	private int size;
	private List<String> sort;
	private Query query;
}
