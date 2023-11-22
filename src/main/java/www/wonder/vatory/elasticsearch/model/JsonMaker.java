package www.wonder.vatory.elasticsearch.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JsonMaker {
	// int, boolean, String, Object, List 중 하나
	private String type;
	
	private String selfVal;
	
	private List<JsonMaker> childList;

	private Map<String, JsonMaker> subMap;

	public String makeJson(int level) {
		String result = "";
		
		switch (type) {
			case "String" : {
				result += wrapWithDQM(selfVal);
			}
			case "List" : {
				result += this.makeListJson(level);
			}
			case "Object" : {
				result += this.makeObjectJson(level);
			}
			default : {
				result += selfVal;
			}
		}
		
		return result;
	}
	
	private String makeListJson(int level) {
		// 일단 리스트니까 대괄호 열고 시작
		String result = "[\n";
		
		// childList의 각 JsonMaker에 대해
		int listSize = childList.size();
		for (int i = 0; i < listSize; i++) {
			// 안쪽 처리로 들여쓰고
			result += innerTab(level);
			// 해당 메이커로 JSON 만들고
			childList.get(i).makeJson(level + 1);
			// 마지막이 아니면 콤마 찍고
			if (i < listSize - 1) {
				result += ",";
			}
			// 엔터로 마무리
			result += "\n";
		}
		
		// 바깥 처리로 들여쓴 후 닫아서 마무리
		result += outerTab(level) + "]";
		return result;
	}
	
	private String makeObjectJson(int level) {
		// 일단 객체니까 중괄호 열고 시작
		String result = "{\n";
		
		// 일단 subMap에서 key들을 전부 꺼내서 List에 담자
		List<String> keyList = takeAllKeys(subMap);
		for(  ){
			// 안쪽 처리로 들여쓰고
			result += innerTab(level);
			// 먼저 key를 꺼내 DQM을 씌운 다음 키임을 명시한 후
			result += wrapWithDQM(pair.getKey()) + " : ";
			// value를 꺼내 Json 만들고
			result += pair.getValue().makeJson(level + 1);
        }
		
		
		// 레벨만큼 들여쓴 후 닫기
		result += outerTab(level) + "}";
		return result;
	}
	
	private List<String> takeAllKeys(Map<String, Object> map) {
		List<String> result = new ArrayList<>();
		map.forEach((key, v) -> {
			result.add(key);
		});
		return result;
	}

	private String outerTab(int level) {
		return "\t".repeat(level);
	}
	
	private String innerTab(int level) {
		return "\t".repeat(level + 1);
	}
	
	private String wrapWithDQM(String str) {
		return "\"" + str + "\"";
	}

}
