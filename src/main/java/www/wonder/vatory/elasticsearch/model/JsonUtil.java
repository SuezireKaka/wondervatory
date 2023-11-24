package www.wonder.vatory.elasticsearch.model;

import java.util.ArrayList;
import java.util.List;

public abstract class JsonUtil {
	private static final String STRING_TYPE = "String";
	private static final String OBJECT_TYPE = "Object";
	private static final String LIST_TYPE = "list";
	private static final String INT_TYPE = "int";
	
	private static final String ASC_SORT = "asc";
	private static final String DESC_SORT = "desc";
	
	private static final String MAX_SEARCH = "10000";
	
	public static String makeWorkQuaryShell(List<JsonMaker> must, JsonMaker agg) {

		
		String[] dtoPropArray = {"size", "sort", "query", "agg"};
		JsonMaker dto = JsonMaker.builder().type(OBJECT_TYPE)
				.propertyArray(dtoPropArray)
				.build();
		
		List<JsonMaker> dtoChildList = new ArrayList<>();
		
		
		
		JsonMaker dtoSize = JsonMaker.makeSimpleMaker(INT_TYPE, MAX_SEARCH);
		
		dtoChildList.add(dtoSize);
		
		
		
		JsonMaker dtoSort_0_Time = JsonMaker.makeSimpleMaker(STRING_TYPE, DESC_SORT);
		
		String[] dtoSort_0_PropArray = {"time"};
		List<JsonMaker> dtoSort_0_ChildList = new ArrayList<>();
		dtoSort_0_ChildList.add(dtoSort_0_Time);
		JsonMaker dtoSort_0 = JsonMaker.makeObjectMaker(dtoSort_0_PropArray, dtoSort_0_ChildList);
		
		List<JsonMaker> dtoSortChildList = new ArrayList<>();
		dtoSortChildList.add(dtoSort_0);
		JsonMaker dtoSort = JsonMaker.makeListMaker(dtoSortChildList);
		
		dtoChildList.add(dtoSort);
		
		
		
		JsonMaker dtoQueryBoolMust = JsonMaker.makeListMaker(must);
		
		String[] dtoQueryBoolPropArray = {"must"};
		List<JsonMaker> dtoQueryBoolChildList = new ArrayList<>();
		dtoQueryBoolChildList.add(dtoQueryBoolMust);
		JsonMaker dtoQueryBool = JsonMaker.makeObjectMaker(dtoQueryBoolPropArray, dtoQueryBoolChildList);
		
		String[] dtoQueryPropArray = {"bool"};
		List<JsonMaker> dtoQueryChildList = new ArrayList<>();
		dtoQueryChildList.add(dtoQueryBool);
		JsonMaker dtoQuery = JsonMaker.makeObjectMaker(dtoQueryPropArray, dtoQueryChildList);
		
		dtoChildList.add(dtoQuery);
		
		
		
		dto.setChildList(dtoChildList);
		
		return dto.makeJson(0);
	};
	
	public static JsonMaker makeConditionJsonMaker(String type, String columnName, String gte, String lt) {
		switch (type) {
		case "range" :
			break;
		case "match" :
			break;
		case "regexp" :
			break;
		default :
			return JsonMaker.builder().build();
		}
		return JsonMaker.builder().build();
		
	}
	
	public static JsonMaker makeAggregationJsonMaker() {
		
		return JsonMaker.builder().build();
	}
}
