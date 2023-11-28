package www.wonder.vatory.elasticsearch.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class JsonUtil {
	private static final String STRING_TYPE = "String";
	private static final String OBJECT_TYPE = "Object";
	private static final String LIST_TYPE = "List";
	private static final String INT_TYPE = "int";
	
	private static final String ASC_SORT = "asc";
	private static final String DESC_SORT = "desc";
	
	private static final String SIZE = "0";
	
	private static final String RANGE_CONDI = "range";
	private static final String MATCH_CONDI = "match";
	private static final String REGEXP_CONDI = "regexp";
	
	
	private static final String BY_DAY_AGGS = "byDay";
	private static final String HISTOGRAM = "date_histogram";
	
	public static String makeWorkQuaryShell(List<JsonMaker> must, JsonMaker aggs) {

		JsonMaker dto;
		
		if (aggs.getChildList().size() == 0) {
			String[] dtoPropArray = {"size", "sort", "query"};
			dto = JsonMaker.builder().type(OBJECT_TYPE)
				.propertyArray(dtoPropArray)
				.build();
		}
		else {
			String[] dtoPropArray = {"size", "sort", "query", "aggs"};
			dto = JsonMaker.builder().type(OBJECT_TYPE)
					.propertyArray(dtoPropArray)
					.build();
		}
		
		
		List<JsonMaker> dtoChildList = new ArrayList<>();
		
		
		
		JsonMaker dtoSize = JsonMaker.makeSimpleMaker(INT_TYPE, SIZE);
		
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
		
		if (aggs.getChildList().size() > 0) {
			dtoChildList.add(aggs);
		}
		
		dto.setChildList(dtoChildList);
		
		return dto.makeJson(0);
	};
	
	public static JsonMaker makeRangeJsonMaker(String columnName, String gte, String lt) {
		
		JsonMaker condiRangeColumnGte = JsonMaker.makeSimpleMaker(STRING_TYPE, gte);
		JsonMaker condiRangeColumnlt = JsonMaker.makeSimpleMaker(STRING_TYPE, lt);

		String[] condiRangeColumnPropArray = {"gte", "lt"};
		List<JsonMaker> condiRangeColumnChildList = new ArrayList<>();
		condiRangeColumnChildList.addAll(Arrays.asList(condiRangeColumnGte, condiRangeColumnlt));
		JsonMaker condiRangeColumn = JsonMaker.makeObjectMaker
				(condiRangeColumnPropArray, condiRangeColumnChildList);
		
		String[] condiRangePropArray = {columnName};
		List<JsonMaker> condiRangeChildList = new ArrayList<>();
		condiRangeChildList.add(condiRangeColumn);
		JsonMaker condiRange = JsonMaker.makeObjectMaker(condiRangePropArray, condiRangeChildList);
		
		String[] condiPropArray = {RANGE_CONDI};
		List<JsonMaker> condiChildList = new ArrayList<>();
		condiChildList.add(condiRange);
		JsonMaker condi = JsonMaker.makeObjectMaker(condiPropArray, condiChildList);
		
		return condi;
	}
	
	public static JsonMaker makeMatchJsonMaker(String columnName, String val) {
		
		JsonMaker condiMatchColumn = JsonMaker.makeSimpleMaker(STRING_TYPE, val);
		
		String[] condiMatchPropArray = {columnName};
		List<JsonMaker> condiMatchChildList = new ArrayList<>();
		condiMatchChildList.add(condiMatchColumn);
		JsonMaker condiMatch = JsonMaker.makeObjectMaker(condiMatchPropArray, condiMatchChildList);
		
		String[] condiPropArray = {MATCH_CONDI};
		List<JsonMaker> condiChildList = new ArrayList<>();
		condiChildList.add(condiMatch);
		JsonMaker condi = JsonMaker.makeObjectMaker(condiPropArray, condiChildList);
		
		return condi;
	}
	
	public static JsonMaker makeRegExpJsonMaker(String columnName, String regexp) {
		
		JsonMaker condiRegExpColumn = JsonMaker.makeSimpleMaker(STRING_TYPE, regexp);
		
		String[] condiRegExpPropArray = {columnName};
		List<JsonMaker> condiRegExpChildList = new ArrayList<>();
		condiRegExpChildList.add(condiRegExpColumn);
		JsonMaker condiRegExp = JsonMaker.makeObjectMaker(condiRegExpPropArray, condiRegExpChildList);
		
		String[] condiPropArray = {REGEXP_CONDI};
		List<JsonMaker> condiChildList = new ArrayList<>();
		condiChildList.add(condiRegExp);
		JsonMaker condi = JsonMaker.makeObjectMaker(condiPropArray, condiChildList);
		
		return condi;
	}
	
	public static JsonMaker makeAggsJsonMaker(
			String columnName, String interval, String format, String min, String max) {
		
		
		JsonMaker aggsByDayDateHistogramExBoundsMin = JsonMaker.makeSimpleMaker(STRING_TYPE, min);
		JsonMaker aggsByDayDateHistogramExBoundsMax = JsonMaker.makeSimpleMaker(STRING_TYPE, max);
		
		String[] aggsByDayDateHistogramExBoundsPropArray = {"min", "max"};
		List<JsonMaker> aggsByDayDateHistogramExBoundsChildList = new ArrayList<>();
		aggsByDayDateHistogramExBoundsChildList.add(aggsByDayDateHistogramExBoundsMin);
		aggsByDayDateHistogramExBoundsChildList.add(aggsByDayDateHistogramExBoundsMax);
		
		JsonMaker aggsByDayDateHistogramExBounds = JsonMaker.makeObjectMaker(
				aggsByDayDateHistogramExBoundsPropArray, aggsByDayDateHistogramExBoundsChildList);
		
		
		JsonMaker aggsByDayDateHistogramField = JsonMaker.makeSimpleMaker(STRING_TYPE, columnName);
		JsonMaker aggsByDayDateHistogramCalan = JsonMaker.makeSimpleMaker(STRING_TYPE, interval);
		JsonMaker aggsByDayDateHistogramFormat = JsonMaker.makeSimpleMaker(STRING_TYPE, format);
		
		String[] aggsByDayDateHistogramPropArray =
			{"field", "calendar_interval", "format", "extended_bounds"};
		List<JsonMaker> aggsByDayDateHistogramChildList = new ArrayList<>();
		aggsByDayDateHistogramChildList.add(aggsByDayDateHistogramField);
		aggsByDayDateHistogramChildList.add(aggsByDayDateHistogramCalan);
		aggsByDayDateHistogramChildList.add(aggsByDayDateHistogramFormat);
		aggsByDayDateHistogramChildList.add(aggsByDayDateHistogramExBounds);
		
		
		JsonMaker aggsByDayDateHistogram = JsonMaker.makeObjectMaker(
				aggsByDayDateHistogramPropArray, aggsByDayDateHistogramChildList);
		
		String[] aggsByDayPropArray = {HISTOGRAM};
		List<JsonMaker> aggsByDayChildList = new ArrayList<>();
		aggsByDayChildList.add(aggsByDayDateHistogram);
		JsonMaker aggsByDay = JsonMaker.makeObjectMaker(aggsByDayPropArray, aggsByDayChildList);
		
		String[] aggsPropArray = {BY_DAY_AGGS};
		List<JsonMaker> aggsChildList = new ArrayList<>();
		aggsChildList.add(aggsByDay);
		JsonMaker aggs = JsonMaker.makeObjectMaker(aggsPropArray, aggsChildList);
		
		return aggs;
	}
}
