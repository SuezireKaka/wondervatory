package www.wonder.vatory.elasticsearch.model;

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
public class ElasticResultDTO {
	String seriesReadData;
	String allPostsReadData;
}
