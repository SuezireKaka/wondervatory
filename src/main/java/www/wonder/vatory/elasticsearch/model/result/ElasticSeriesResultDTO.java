package www.wonder.vatory.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElasticSeriesResultDTO extends ElasticResultDTO {
	String seriesReadData;
	String allPostsReadData;
}