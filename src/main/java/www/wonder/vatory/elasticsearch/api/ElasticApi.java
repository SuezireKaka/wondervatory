package www.wonder.vatory.elasticsearch.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class ElasticApi {

	@Value("${elasticsearch.host}")
	private String host;

	@Value("${elasticsearch.port}")
	private int port;

	/**
	 * 엘라스틱서치에서 제공하는 api를 이용한 전송메소드
	 * 
	 * @param method
	 * @param url
	 * @param obj
	 * @param jsonData
	 * @return
	 */
	public Map<String, Object> callElasticApi(String method, String url, Object obj, String jsonData) {
		Map<String, Object> result = new HashMap<>();

		String jsonString;
		// json형태의 파라미터가 아니라면 gson으로 만들어주자.
		if (jsonData == null) {
			Gson gson = new Gson();
			jsonString = gson.toJson(obj);
		} else {
			jsonString = jsonData;
		}

		// 엘라스틱서치에서 제공하는 restClient를 통해 엘라스틱서치에 접속한다
		try (RestClient restClient = RestClient.builder(new HttpHost(host, port)).build()) {
			Map<String, String> params = Collections.singletonMap("pretty", "true");
			// 엘라스틱서치에서 제공하는 response 객체
			Response response = null;

			// DELETE 메소드는 HttpEntity가 필요없다
			if (method.equals("DELETE")) {
				response = restClient.performRequest(method, url, params);
			}
			else {
				HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
				response = restClient.performRequest(method, url, params, entity);

			}
			// 앨라스틱서치에서 리턴되는 응답코드를 받는다
			int statusCode = response.getStatusLine().getStatusCode();
			// 엘라스틱서치에서 리턴되는 응답메시지를 받는다
			String responseBody = EntityUtils.toString(response.getEntity());
			result.put("resultCode", statusCode);
			result.put("resultBody", responseBody);
		}
		catch (Exception e) {
			result.put("resultCode", -1);
			result.put("resultBody", e.toString());
		}
		return result;
	}
}
