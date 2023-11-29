package www.wonder.vatory.elasticsearch.model.jsonmaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import www.wonder.vatory.elasticsearch.model.condi.ElasticCondition;

public class JsonMakerSample {

	public static void main(String[] args) {
		/*
		 * 적당한 트리 표현 스트링으로부터 아래 코드에 해당하는 자바 파일을 만들어 실행하는 프로그램을 짤 수 있을까??
		 */
		sampleMakingFromObject();

	}


	private static void sampleMakingFromObject() {
		ElasticCondition condi = new ElasticCondition();
		condi.setType("regexp");
		condi.setMapping("readerId");
		condi.setValue("readerId");

		System.out.println(JsonMaker.makeObjectMakerFromObject(condi));
	}

	
	// 심심하면 읽어볼 것
	private static void sampleHardMaking() {
		// making "dto"...
		JsonMaker dto_Maker = JsonMaker.builder().type("Object").build();

		// setting "dto"...
		String[] dto_propertyArray = { "size", "sort", "query" };
		dto_Maker.setPropertyArray(dto_propertyArray);
		List<JsonMaker> dto_childList = new ArrayList<>();

		// making "dto.size" and closing...
		JsonMaker size_maker = JsonMaker.builder().type("int").selfVal("100").build();

		// inserting "dto.size" to "dto"...
		dto_childList.add(size_maker);

		// making "dto.sortList"...
		JsonMaker sortList_Maker = JsonMaker.builder().type("List").build();

		// setting "dto.sortList"...
		List<JsonMaker> sortList_childList = new ArrayList<>();

		// making "dto.sortList[0]"...
		JsonMaker sort_0_Maker = JsonMaker.builder().type("Object").build();

		// setting "dto.sortList[0]"...
		String[] sort_0_propertyArray = { "time" };
		sort_0_Maker.setPropertyArray(sort_0_propertyArray);
		List<JsonMaker> sort_0_childList = new ArrayList<>();

		// making "dto.sortList[0].time" and closing...
		JsonMaker time_0_Maker = JsonMaker.builder().type("String").selfVal("desc").build();

		// inserting "dto.sortList[0].time_0" into "dto.sortList[0]."...
		sort_0_childList.add(time_0_Maker);

		// closing "dto.sortList[0]"...
		sort_0_Maker.setChildList(sort_0_childList);

		// inserting "dto.sortList[0]" into "dto.sortList"...
		sortList_childList.add(sort_0_Maker);

		// closing "dto.sortList"...
		sortList_Maker.setChildList(sortList_childList);

		// inserting "dto.sortList" to "dto"...
		dto_childList.add(sortList_Maker);

		// making "dto.query"...
		JsonMaker query_Maker = JsonMaker.builder().type("Object").build();

		// setting "dto.query"...
		String[] query_propertyArray = { "bool" };
		query_Maker.setPropertyArray(query_propertyArray);
		List<JsonMaker> query_childList = new ArrayList<>();

		// making "dto.query.bool"...
		JsonMaker bool_Maker = JsonMaker.builder().type("Object").build();

		// setting "dto.query.bool"...
		String[] bool_propertyArray = { "must" };
		bool_Maker.setPropertyArray(bool_propertyArray);
		List<JsonMaker> bool_childList = new ArrayList<>();

		// making "dto.query.bool.mustList"...
		JsonMaker mustList_Maker = JsonMaker.builder().type("List").build();

		// setting "dto.query.bool.mustList"...
		List<JsonMaker> mustList_childList = new ArrayList<>();

		// making "dto.query.bool.mustList[0~1]"...

		// making "dto.query.bool.mustList[0]"...
		JsonMaker must_0_Maker = JsonMaker.builder().type("Object").build();

		// setting "dto.query.bool.mustList[0]"...
		String[] must_0_propertyArray = { "range" };
		must_0_Maker.setPropertyArray(must_0_propertyArray);
		List<JsonMaker> must_0_childList = new ArrayList<>();

		// making "dto.query.bool.mustList[0].range_0"...
		JsonMaker range_0_Maker = JsonMaker.builder().type("Object").build();

		// setting "dto.query.bool.mustList[0].range_0"...
		String[] range_0_propertyArray = { "time" };
		range_0_Maker.setPropertyArray(range_0_propertyArray);
		List<JsonMaker> range_0_childList = new ArrayList<>();

		// making "dto.query.bool.mustList[0].range_0.time"...
		JsonMaker time_1_Maker = JsonMaker.builder().type("Object").build();

		// setting "dto.query.bool.mustList[0].range_0.time"...
		String[] time_1_propertyArray = { "gte", "lt" };
		time_1_Maker.setPropertyArray(time_1_propertyArray);
		List<JsonMaker> time_1_childList = new ArrayList<>();

		// making "dto.query.bool.mustList[0].range_0.time.gte" and closing...
		JsonMaker gte_0_Maker = JsonMaker.builder().type("String").selfVal("now-3d/d").build();

		// making "dto.query.bool.mustList[0].range_0.time.lt" and closing...
		JsonMaker lt_0_Maker = JsonMaker.builder().type("String").selfVal("now").build();

		// inserting "dto.query.bool.mustList[0].range_0.time.(gte, lt)"
		// into "dto.query.bool.mustList[0].range_0.time"...
		time_1_childList.addAll(Arrays.asList(gte_0_Maker, lt_0_Maker));

		// closing "dto.query.bool.mustList[0].range_0.time"...
		time_1_Maker.setChildList(time_1_childList);

		// inserting "dto.query.bool.mustList[0].range_0.time"
		// into "dto.query.bool.mustList[0].range_0"...
		range_0_childList.add(time_1_Maker);

		// closing "dto.query.bool.mustList[0].range_0"...
		range_0_Maker.setChildList(range_0_childList);

		// inserting "dto.query.bool.mustList[0].range_0" into
		// "dto.query.bool.mustList[0]"...
		must_0_childList.add(range_0_Maker);

		// closing "dto.query.bool.mustList[0]" ...
		must_0_Maker.setChildList(must_0_childList);

		// inserting "dto.query.bool.mustList[0]" into "dto.query.bool.mustList"...
		mustList_childList.add(must_0_Maker);

		// making "dto.query.bool.mustList[1]"...
		JsonMaker must_1_Maker = JsonMaker.builder().type("Object").build();

		// setting "dto.query.bool.mustList[1]"...
		String[] must_1_propertyArray = { "range" };
		must_1_Maker.setPropertyArray(must_1_propertyArray);
		List<JsonMaker> must_1_childList = new ArrayList<>();

		// making "dto.query.bool.mustList[1].range_1"...
		JsonMaker range_1_Maker = JsonMaker.builder().type("Object").build();

		// setting "dto.query.bool.mustList[1].range_1"...
		String[] range_1_propertyArray = { "birth" };
		range_1_Maker.setPropertyArray(range_1_propertyArray);
		List<JsonMaker> range_1_childList = new ArrayList<>();

		// making "dto.query.bool.mustList[1].range_1.birth"...
		JsonMaker birth_Maker = JsonMaker.builder().type("Object").build();

		// setting "dto.query.bool.mustList[1].range_1.birth"...
		String[] birth_propertyArray = { "gte", "lt" };
		birth_Maker.setPropertyArray(birth_propertyArray);
		List<JsonMaker> birth_childList = new ArrayList<>();

		// making "dto.query.bool.mustList[1].range_1.birth.gte" and closing...
		JsonMaker gte_1_Maker = JsonMaker.builder().type("String").selfVal("now-40y").build();

		// making "dto.query.bool.mustList[1].range_1.birth.lt" and closing...
		JsonMaker lt_1_Maker = JsonMaker.builder().type("String").selfVal("now-30y").build();
		

		// inserting "dto.query.bool.mustList[1].range_1.birth(gte, lt)"
		// into "dto.query.bool.mustList[0].range_0.time"...
		birth_childList.addAll(Arrays.asList(gte_1_Maker, lt_1_Maker));

		// closing "dto.query.bool.mustList[1].range_1.birth"...
		birth_Maker.setChildList(birth_childList);

		// inserting "dto.query.bool.mustList[1].range_1.birth"
		// into "dto.query.bool.mustList[1].range_1"...
		range_1_childList.add(birth_Maker);

		// closing "dto.query.bool.mustList[1].range_1"...
		range_1_Maker.setChildList(range_1_childList);

		// inserting "dto.query.bool.mustList[1].range_1" into
		// "dto.query.bool.mustList[1]"...
		must_1_childList.add(range_1_Maker);

		// closing "dto.query.bool.mustList[1]" ...
		must_1_Maker.setChildList(must_1_childList);

		// inserting "dto.query.bool.mustList[1]" into "dto.query.bool.mustList"...
		mustList_childList.add(must_1_Maker);

		// closing "dto.query.bool.mustList" ...
		mustList_Maker.setChildList(mustList_childList);

		// inserting "dto.query.bool.mustList" into "dto.query.bool"...
		bool_childList.add(mustList_Maker);

		// closing "dto.query.bool"...
		bool_Maker.setChildList(bool_childList);

		// inserting "dto.query.bool" into "dto.query"...
		query_childList.add(bool_Maker);

		// closing "dto.query"
		query_Maker.setChildList(query_childList);

		// inserting "dto.query" to "dto"...
		dto_childList.add(query_Maker);

		// closing "dto"...
		dto_Maker.setChildList(dto_childList);

		System.out.println(dto_Maker.makeJson(0));
	}
	
	

}
