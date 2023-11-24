package www.wonder.vatory.elasticsearch.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.wonder.vatory.elasticsearch.api.ElasticApi;
import www.wonder.vatory.elasticsearch.model.JsonMaker;
import www.wonder.vatory.elasticsearch.model.JsonUtil;
import www.wonder.vatory.work.model.ReadTableDTO;

@Service
public class ElasticService {
	@Autowired
	ElasticApi elasticApi;
	
	private final String ELASTIC_INDEX = "wondervatory_read";
	private final String ELASTIC_TYPE = "_search";

	private final String SEX_KEY = "sex";
	private final String AGE_KEY = "age";
	
	private final String NOW_DATE = "now";
	private final String FROM_NOW_DATE = NOW_DATE + "-";
	private final String DAY_START_POINT = "d/d";
	private final String YEAR_START_POINT = "y/y";
	
	private final String TIME_COLUMN = "time";
	private final String BIRTH_COLUMN = "birth";
	
	private final String RANGE_CONDI = "range";
	private final String MATCH_CONDI = "match";
	private final String REGEXP_CONDI = "regexp";
	
	public ReadTableDTO listLatestRead(String workId, int daynum, String condi) {
		ReadTableDTO result = new ReadTableDTO();
		
		// json 만들어주세요!!!
		String request = buildElasticJSON(workId, daynum, condi);
		// json 받아서 실행
		Object statisticalRead = getElasticResult(request);
		
		return result;
	}
	
	private String buildElasticJSON(String workId, int daynum, String condi) {
		
		Map<String, String> condiMapping = mapCondi(condi);
		
		String timeGte = FROM_NOW_DATE + Integer.toString(daynum) + DAY_START_POINT;
		String timeLt = NOW_DATE;
		
		JsonMaker timeRangeCondi = JsonUtil.makeConditionJsonMaker
				(RANGE_CONDI, TIME_COLUMN, timeGte, timeLt);
		
		String gteAge = Integer.toString(Integer.parseInt(condiMapping.get(AGE_KEY)) + 10);
		
		String birthGte = FROM_NOW_DATE + gteAge + YEAR_START_POINT;
		String birthLt = FROM_NOW_DATE + condiMapping.get(AGE_KEY) + YEAR_START_POINT;
		
		JsonMaker birthRangeCondi = JsonUtil.makeConditionJsonMaker
				(RANGE_CONDI, BIRTH_COLUMN, birthGte, birthLt);
		
		List<JsonMaker> mustList = new ArrayList<>();
		
		mustList.addAll(Arrays.asList(timeRangeCondi, birthRangeCondi));
		
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
