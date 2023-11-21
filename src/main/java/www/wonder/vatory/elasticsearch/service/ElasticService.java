package www.wonder.vatory.elasticsearch.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.wonder.vatory.elasticsearch.model.searchCore.BirthRangeCore;
import www.wonder.vatory.elasticsearch.model.searchCore.MatchCore;
import www.wonder.vatory.elasticsearch.model.searchCore.RegExpCore;
import www.wonder.vatory.elasticsearch.model.searchCore.TimeRange;
import www.wonder.vatory.elasticsearch.model.searchCore.TimeRangeCore;
import www.wonder.vatory.work.model.ReadVO;

@Service
public class ElasticService {
	@Autowired
	RestClient restClient;
	
	public List<ReadVO> listLatestRead(String workId, int daynum, String condi) {
		// json 만들어주세요!!!
		String request = buildElasticJSON(workId, daynum, condi);
		// json 받아서 실행
		
		return null;
	}
	
	private String buildElasticJSON(String workId, int daynum, String condi) {
		Map<String, String> condiMapping = mapCondi(condi);
		
		String sex = condiMapping.get("sex");
		if (sex == null) {
			
		}
		MatchCore matchCore = new MatchCore();
		
		RegExpCore regexpCore = new RegExpCore(workId + "(....)*");
		
		TimeRange timeRange = TimeRange.builder()
				.gte("now-" + daynum + "d/d").lt("now")
				.build();
		TimeRangeCore timeCore = new TimeRangeCore(timeRange);
		
		int age = Integer.parseInt(condiMapping.get("age"));
		TimeRange birthRange = TimeRange.builder()
				.gte("now-" + (age + 10) + "y").lt("now-" + (age) + "y")
				.build();
		BirthRangeCore birthCore = new BirthRangeCore(birthRange);
		
		
		
		return "1";
	}

	private Map<String, String> mapCondi(String condi) {
		List<String> splitedCondi = Arrays.asList(condi.split("-"));
		Map<String, String> condiMapping = new HashMap<>();
		for (String str : splitedCondi) {
			String[] splitedStr = str.split("_");
			String key = splitedStr[0];
			String val = splitedStr[1];
			
			if (val.equals("any")) {
				continue;
			}
			
			if (key.equals("male")) {
				val = "남성";
			}
			else if (key.equals("female")) {
				val = "여성";
			}
			condiMapping.put(key, val);
		}
		return condiMapping;
	}
	
	private List<ReadVO> getElasticResult(String requestJSON) {
		
		Request request = new Request("GET", "logstash_maria_read");

		NStringEntity entity = new NStringEntity(requestJSON, ContentType.APPLICATION_JSON);
		request.setEntity(entity);

		Response response;
		try {
			response = restClient.performRequest(request);
			List<ReadVO> result = response.getEntity();
			
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
}
