package www.wonder.vatory.elasticsearch.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.wonder.vatory.elasticsearch.api.ElasticApi;
import www.wonder.vatory.elasticsearch.model.ElasticPostResultDTO;
import www.wonder.vatory.elasticsearch.model.ElasticResultDTO;
import www.wonder.vatory.elasticsearch.model.ElasticSeriesResultDTO;
import www.wonder.vatory.elasticsearch.model.JsonMaker;
import www.wonder.vatory.elasticsearch.model.JsonUtil;
import www.wonder.vatory.work.mapper.WorkMapper;
import www.wonder.vatory.work.model.PostVO;

@Service
public class ElasticService {
	@Autowired
	ElasticApi elasticApi;
	
	@Autowired
	WorkMapper workMapper;
	
	private final String ELASTIC_INDEX = "wondervatory_read";
	private final String ELASTIC_TYPE = "_search";
	private final String URL = ELASTIC_INDEX + "/" + ELASTIC_TYPE;

	private final String SEX_KEY = "sex";
	private final String AGE_KEY = "age";
	
	private final String NOW_DATE = "now";
	private final String FROM_NOW_DATE = NOW_DATE + "-";
	private final String DAY = "d";
	private final String DAY_START_POINT = DAY + "/" + DAY;
	private final String YEAR_START_POINT = "y/y";
	
	private final String ID_REG_EXP = "(....)+";
	
	private final String READEE_COLUMN = "readeeId";
	private final String SEX_COLUMN = "sex";
	private final String TIME_COLUMN = "time";
	private final String BIRTH_COLUMN = "birth";
	
	private final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	private ElasticResultDTO getLatestReadOf(int requestNum, String workId, int daynum, String condi) {
		// json 만들어주세요!!!
		String[] request = buildElasticJSON(requestNum, workId, daynum, condi, mapCondi(condi));
		// json 받아서 실행
		ElasticResultDTO result = getElasticResult(request);
		
		return result;
	}
	
	private String[] buildElasticJSON(int requestNum,
			String workId, int daynum, String condi, Map<String, String> condiMapping) {
		
		String[] result = new String[requestNum];
		
		result[0] = makeElasticJson(workId, daynum, condiMapping);
		
		if (requestNum > 1) {
			result[1] = makeElasticJson(workId + ID_REG_EXP, daynum, condiMapping);
		}
		
		return result;
	}

	private String makeElasticJson(String workId, int daynum, Map<String, String> condiMapping) {
		// id 검색
		JsonMaker idRegExpCondi = JsonUtil.makeRegExpJsonMaker(READEE_COLUMN, workId);
		
		// 시간 검색
		String timeGte = FROM_NOW_DATE + Integer.toString(daynum) + DAY_START_POINT;
		String timeLt = NOW_DATE;
		
		JsonMaker timeRangeCondi = JsonUtil.makeRangeJsonMaker(TIME_COLUMN, timeGte, timeLt);
		
		List<JsonMaker> mustList = new ArrayList<>();
		
		// must 생성
		mustList.addAll(Arrays.asList(idRegExpCondi, timeRangeCondi));
		
		// 성별
		if (condiMapping.get(SEX_KEY) != null) {
			JsonMaker sexMatchCondi = JsonUtil.makeMatchJsonMaker(SEX_COLUMN, condiMapping.get(SEX_KEY));
			
			mustList.add(sexMatchCondi);
		}
				
		// 나이 검색
		if (condiMapping.get(AGE_KEY) != null) {
			String gteAge = Integer.toString(Integer.parseInt(condiMapping.get(AGE_KEY)) + 10);
			String birthGte = FROM_NOW_DATE + gteAge + YEAR_START_POINT;
			String birthLt = FROM_NOW_DATE + condiMapping.get(AGE_KEY) + YEAR_START_POINT;
			
			JsonMaker birthRangeCondi = JsonUtil.makeRangeJsonMaker(BIRTH_COLUMN, birthGte, birthLt);
			
			mustList.add(birthRangeCondi);
		}
		
		// 집계 부분 생성
		JsonMaker aggs = JsonUtil.makeAggsJsonMaker(
				TIME_COLUMN, "1" + DAY, DEFAULT_DATE_FORMAT, timeGte, timeLt);
		
		String json = JsonUtil.makeWorkQuaryShell(mustList, aggs);
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
	
	private ElasticResultDTO getElasticResult(String[] requestArray) {
		ElasticResultDTO result;

		
		// 요청이 하나만 오면 포스트 아니면 시리즈랑 전체
		if (requestArray.length == 1) {
			ElasticPostResultDTO lemma = new ElasticPostResultDTO();
			
			Map<String, Object> seriesMap = elasticApi.callElasticApi("GET", URL, null, requestArray[0]);
			lemma.setPostReadData((String) seriesMap.get("resultBody"));
			
			result = (ElasticResultDTO) lemma;
		}
		else {
			ElasticSeriesResultDTO lemma = new ElasticSeriesResultDTO();
			
			Map<String, Object> seriesMap = elasticApi.callElasticApi("GET", URL, null, requestArray[0]);
			lemma.setSeriesReadData((String) seriesMap.get("resultBody"));
			
			Map<String, Object> allPostsMap = elasticApi.callElasticApi("GET", URL, null, requestArray[1]);
			lemma.setAllPostsReadData((String) allPostsMap.get("resultBody"));
			
			result = (ElasticResultDTO) lemma;
		}
		
		return result;
	}
	
	public ElasticResultDTO getLatestReadOfSeries(String seriesId, int daynum, String condi) {
		// 시리즈 자체 클릭 수와 시리즈가 갖고 있는 포스트 조회 수의 총합을 가져와야 하니까 두 개
		final int REQUEST_NUM = 2;
		
		return getLatestReadOf(REQUEST_NUM, seriesId, daynum, condi);
	}

	public ElasticResultDTO getLatestReadOfEpinum(String seriesId,
			int epinum, int daynum, String condi) {
		// 주어진 포스트에 대해서만 가져오면 되니까 1
		final int REQUEST_NUM = 1;
		
		PostVO target = workMapper.findPostByEpinum(seriesId, epinum - 1);
		String postId = target.getId();
		
		return getLatestReadOf(REQUEST_NUM, postId, daynum, condi);
	}
}
