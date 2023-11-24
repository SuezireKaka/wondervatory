package www.wonder.vatory.elasticsearch.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import www.wonder.vatory.elasticsearch.api.ElasticApi;
import www.wonder.vatory.elasticsearch.model.searchInput.condition.Condition;
import www.wonder.vatory.elasticsearch.model.searchInput.condition.MatchCondition;
import www.wonder.vatory.elasticsearch.model.searchInput.condition.RangeCondition;
import www.wonder.vatory.elasticsearch.model.searchInput.condition.RegExpCondition;
import www.wonder.vatory.elasticsearch.model.searchInput.searchCore.BirthRangeCore;
import www.wonder.vatory.elasticsearch.model.searchInput.searchCore.MatchCore;
import www.wonder.vatory.elasticsearch.model.searchInput.searchCore.RegExpCore;
import www.wonder.vatory.elasticsearch.model.searchInput.searchCore.TimeRange;
import www.wonder.vatory.elasticsearch.model.searchInput.searchCore.TimeRangeCore;
import www.wonder.vatory.elasticsearch.model.searchInput.shell.Bool;
import www.wonder.vatory.elasticsearch.model.searchInput.shell.ElasticQueryDTO;
import www.wonder.vatory.elasticsearch.model.searchInput.shell.Query;
import www.wonder.vatory.work.model.ReadVO;

@Service
public class ElasticService {
	@Autowired
	ElasticApi elasticApi;
	
	private final String ELASTIC_INDEX = "wondervatory_read";
	private final String ELASTIC_TYPE = "_search";
	
	public Object listLatestRead(String workId, int daynum, String condi) {
		// json 만들어주세요!!!
		String request = buildElasticJSON(workId, daynum, condi);
		// json 받아서 실행
		Object statisticalReadList = getElasticResult(request);
		
		return statisticalReadList;
	}
	
	private String buildElasticJSON(String workId, int daynum, String condi) {
		
		
		
		
		Map<String, String> condiMapping = mapCondi(condi);
		
		List<Condition> must = new ArrayList<>();
		
		RegExpCore regexpCore = new RegExpCore(workId + "(....)*");
		RegExpCondition idCondition = new RegExpCondition(regexpCore);
		
		must.add((Condition) idCondition);
		
		TimeRange timeRange = TimeRange.builder()
				.gte("now-" + daynum + "d/d").lt("now")
				.build();
		TimeRangeCore timeCore = new TimeRangeCore(timeRange);
		RangeCondition timeCondition = new RangeCondition(timeCore);
		
		must.add(timeCondition);
		
		
		if (condiMapping.containsKey("sex")) {
			String sex = condiMapping.get("sex");
			MatchCore matchCore = new MatchCore(sex);
			MatchCondition sexCondition = new MatchCondition(matchCore);
			
			must.add(sexCondition);
		}
		
		if (condiMapping.containsKey("age")) {
			int age = Integer.parseInt(condiMapping.get("age"));
			TimeRange birthRange = TimeRange.builder()
					.gte("now-" + (age + 10) + "y").lt("now-" + (age) + "y")
					.build();
			BirthRangeCore birthCore = new BirthRangeCore(birthRange);
			RangeCondition birthCondition = new RangeCondition(birthCore);
			
			must.add(birthCondition);
		}
		
		List<String> sort = new ArrayList<>();
		sort.add("time");
		
		Bool bool = new Bool(must);
		Query query = new Query(bool);
		ElasticQueryDTO queryDto = ElasticQueryDTO.builder()
				.size(10000).sort(sort).query(query)
				.build();
		
		ObjectMapper mapper = new ObjectMapper();
		
		String json = "";
		try {
			json = mapper.writeValueAsString(queryDto);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return json;
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
			
			if (val.equals("male")) {
				val = "남성";
			}
			else if (val.equals("female")) {
				val = "여성";
			}
			condiMapping.put(key, val);
		}
		return condiMapping;
	}
	
	private Object getElasticResult(String requestJSON) {
		String url = ELASTIC_INDEX + "/" + ELASTIC_TYPE;

		Map<String, Object> result = elasticApi.callElasticApi("GET", url, null, requestJSON);
		
		String success = "성공~";
		
		return result.get("resultBody");
	}
}
