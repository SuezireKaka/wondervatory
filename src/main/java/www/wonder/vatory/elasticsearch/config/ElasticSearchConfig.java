package www.wonder.vatory.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchClient {
	@Value("${elastic.hostname}")
	private String ELASTICSEARCH_HOSTNAME;
	@Value("${elastic.portnum}")
	private int ELASTICSEARCH_PORTNUM;

	@Bean
	public RestClient createClient() {

		RestClientBuilder builder = RestClient.builder(
				new HttpHost(ELASTICSEARCH_HOSTNAME, ELASTICSEARCH_PORTNUM, "https"));

		return builder.build();
	}
}
