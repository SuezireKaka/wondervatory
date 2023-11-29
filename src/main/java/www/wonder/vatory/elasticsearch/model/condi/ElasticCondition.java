package www.wonder.vatory.elasticsearch.model.condi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.elasticsearch.anno.ElasticLevel;
import www.wonder.vatory.elasticsearch.anno.ElasticMapping;
import www.wonder.vatory.elasticsearch.anno.ElasticRange;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElasticCondition {
	@ElasticLevel(0)
	private String type;
	
	@ElasticLevel(1)
	@ElasticMapping
	private String mapping;
	
	@ElasticLevel(2)
	@ElasticRange(seperator = "~")
	private String value;
}
