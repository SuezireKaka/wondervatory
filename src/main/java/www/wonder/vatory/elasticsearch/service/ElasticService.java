package www.wonder.vatory.elasticsearch.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.wonder.vatory.elasticsearch.api.ElasticApi;
import www.wonder.vatory.elasticsearch.model.jsonmaker.JsonMaker;
import www.wonder.vatory.elasticsearch.model.jsonmaker.JsonUtil;
import www.wonder.vatory.elasticsearch.model.result.ElasticDashBoardResultVO;
import www.wonder.vatory.elasticsearch.model.result.ElasticPostResultVO;
import www.wonder.vatory.elasticsearch.model.result.ElasticResultVO;
import www.wonder.vatory.elasticsearch.model.result.ElasticSeriesResultVO;
import www.wonder.vatory.work.mapper.WorkMapper;
import www.wonder.vatory.work.model.PostVO;

@Service
public class ElasticService {
	@Autowired
	ElasticApi elasticApi;
	
	@Autowired
	WorkMapper workMapper;

	private final String SEX_KEY = "sex";
	private final String AGE_KEY = "age";
	
	private final String NOW_DATE = "now";
	private final String FROM_NOW_DATE = NOW_DATE + "-";
	private final String DAY = "d";
	private final String DAY_START_POINT = DAY + "/" + DAY;
	private final String YEAR_START_POINT = "y/y";
	
	private final String ID_COLUMN = "id";
	private final String ID_REG_EXP = "(....)+";
	
	private final String READEE_COLUMN = "readeeId";
	private final String SEX_COLUMN = "sex";
	private final String TIME_COLUMN = "time";
	private final String BIRTH_COLUMN = "birth";
	
	private final String REG_DT_COLUMN = "regDt";
	private final String DESC_COLUMN = "descrim";
	
	private final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	private ElasticResultVO getLatestReadOf(int requestNum, String workId, int daynum, String condi) {
		// json 만들어주세요!!!
		String[] request = buildElasticJSON(requestNum, workId, daynum, condi, mapCondi(condi));
		// json 받아서 실행
		
		String[] url = {"wondervatory_read/_search", "wondervatory_read/_search"};
		ElasticResultVO result = getElasticResult(request, url);
		
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
		JsonMaker aggs = JsonUtil.makeHistogramAggsJsonMaker(
				TIME_COLUMN, "1" + DAY, DEFAULT_DATE_FORMAT, timeGte, timeLt);
		
		String json = JsonUtil.makeQuaryShell(TIME_COLUMN, mustList, aggs).makeJson(0);
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
	
	private ElasticResultVO getElasticResult(String[] requestArray, String[] url) {
		return getElasticResult(requestArray, url, false);
	}

	private ElasticResultVO getElasticResult(
			String[] requestArray, String[] url, boolean isDashBoard) {
		ElasticResultVO result;

		
		// 요청이 하나만 오면 포스트 아니면 시리즈랑 전체
		if (requestArray.length == 1) {
			ElasticPostResultVO lemma = new ElasticPostResultVO();
			
			Map<String, Object> seriesMap = elasticApi.callElasticApi("GET", url[0], null, requestArray[0]);
			lemma.setPostReadData((String) seriesMap.get("resultBody"));
			
			result = (ElasticResultVO) lemma;
		}
		else if (isDashBoard) {
			ElasticDashBoardResultVO lemma = new ElasticDashBoardResultVO();
			
			Map<String, Object> seriesMap = elasticApi.callElasticApi("GET", url[0], null, requestArray[0]);
			lemma.setUpData((String) seriesMap.get("resultBody"));
			
			Map<String, Object> allPostsMap = elasticApi.callElasticApi("GET", url[1], null, requestArray[1]);
			lemma.setDownData((String) allPostsMap.get("resultBody"));
			
			result = (ElasticResultVO) lemma;
		}
		else {		
			ElasticSeriesResultVO lemma = new ElasticSeriesResultVO();
			
			Map<String, Object> seriesMap = elasticApi.callElasticApi("GET", url[0], null, requestArray[0]);
			lemma.setSeriesReadData((String) seriesMap.get("resultBody"));
			
			Map<String, Object> allPostsMap = elasticApi.callElasticApi("GET", url[1], null, requestArray[1]);
			lemma.setAllPostsReadData((String) allPostsMap.get("resultBody"));
			
			result = (ElasticResultVO) lemma;
		}
		
		return result;
	}
	
	public ElasticResultVO getLatestReadOfSeries(String seriesId, int daynum, String condi) {
		// 시리즈 자체 클릭 수와 시리즈가 갖고 있는 포스트 조회 수의 총합을 가져와야 하니까 두 개
		final int REQUEST_NUM = 2;
		
		return getLatestReadOf(REQUEST_NUM, seriesId, daynum, condi);
	}

	public ElasticResultVO getLatestReadOfEpinum(String seriesId,
			int epinum, int daynum, String condi) {
		// 주어진 포스트에 대해서만 가져오면 되니까 1
		final int REQUEST_NUM = 1;
		
		PostVO target = workMapper.findPostByEpinum(seriesId, epinum - 1);
		String postId = target.getId();
		
		return getLatestReadOf(REQUEST_NUM, postId, daynum, condi);
	}

	public ElasticResultVO getDashBoard(String index, String startTime, String endTime) {
		
		String[] urlArray = new String[2];
		
		urlArray[0] = urlArray[1] = index + "/_search";
		
		String[] descrimArray = new String[2];
		
		descrimArray[0] = index.endsWith("account") ? "kakao" : "Series";
		descrimArray[1] = index.endsWith("account") ? "wonder" : "Post";
		
		String[] requestArr = new String[2];
		
		requestArr[0] = makeCumulativeQuery(startTime, endTime, descrimArray[0]);
		requestArr[1] = makeCumulativeQuery(startTime, endTime, descrimArray[1]);
		
		ElasticResultVO dashBoard = getElasticResult(requestArr, urlArray, true);
		
		return dashBoard;
	}

	private String makeCumulativeQuery(String startTime, String endTime, String descrim) {
		List<JsonMaker> mustList = new ArrayList<>();
		
		JsonMaker regRangeCondi = JsonUtil.makeRangeJsonMaker(REG_DT_COLUMN, startTime, endTime);
		mustList.add(regRangeCondi);
		
		JsonMaker descrimMatchCondi = JsonUtil.makeMatchJsonMaker(DESC_COLUMN, descrim);
		mustList.add(descrimMatchCondi);
		
		
		
		JsonMaker cumul = JsonUtil.makeCumulCountAggsJsonMaker(ID_COLUMN, "cnt");
		
		JsonMaker aggs = JsonUtil.makeHistogramAggsJsonMaker(
				REG_DT_COLUMN, "1" + DAY, DEFAULT_DATE_FORMAT, startTime, endTime, cumul);
		
		
		
		String json = JsonUtil.makeQuaryShell(REG_DT_COLUMN, mustList, aggs).makeJson(0);
		
		return json;
	}


}










