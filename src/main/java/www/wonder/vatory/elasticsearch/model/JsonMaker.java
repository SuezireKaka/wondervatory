package www.wonder.vatory.elasticsearch.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Rest_Body를 자유롭게(= 직접적인 클래스를 구현하지 않고) JSON 형태로 만들어주는 클래스
 * 바깥부터 Builder로 만들고 안쪽부터 Setter로 만드는 것을 권장
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonMaker {
	public static final String TAB = "  ";
	
	// int, boolean, String, Object, List 중 하나
	private String type;
	
	private String selfVal;
	
	private String[] propertyArray;
	
	private List<JsonMaker> childList;

	public String makeJson(int level) {
		if (this.type.equals(null)) {
			return "null";
		}
		
		String result = "";
		
		switch (type) {
			case "String" : {
				result += wrapWithDQM(selfVal);
				break;
			}
			case "List" : {
				result += this.makeListJson(level);
				break;
			}
			case "Object" : {
				result += this.makeObjectJson(level);
				break;
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
			result += childList.get(i).makeJson(level + 1);
			result = finallizeElemDeco(result, listSize, i);
		}
		
		// 바깥 처리로 들여쓴 후 닫아서 마무리
		result += outerTab(level) + "]";
		return result;
	}

	private String makeObjectJson(int level) {
		// 일단 객체니까 중괄호 열고 시작
		String result = "{\n";
		
		int propLength = propertyArray.length;
		
		// 일단 subMap에서 key들을 전부 꺼내서 List에 담자 - map이 알아서 순서를 맞춰서 폐기
		for (int i = 0; i < propLength; i++){
			// 안쪽 처리로 들여쓰고
			result += innerTab(level);
			
			// index 범위 초과시 null을 담는 처리
			try {
				// 먼저 key를 꺼내 DQM을 씌운 다음 키임을 명시한 후
				result += wrapWithDQM(propertyArray[i]) + " : ";
				// value를 꺼내 Json 만들고
				result += childList.get(i).makeJson(level + 1);
			}
			// 만약 인덱스에 에러가 생겼다면 property가 child보다 많은 거니까 null로 간주
			catch ( NullPointerException | IndexOutOfBoundsException e ) {
				result += "null";
			}
			finally {
				result = finallizeElemDeco(result, propLength, i);
			}
        }
		
		
		// 레벨만큼 들여쓴 후 닫기
		result += outerTab(level) + "}";
		return result;
	}
	
	private String finallizeElemDeco(String result, int listSize, int i) {
		// 마지막이 아니면 콤마 찍고
		if (i < listSize - 1) {
			result += ",";
		}
		// 엔터로 마무리
		result += "\n";
		return result;
	}

	private String outerTab(int level) {
		return TAB.repeat(level);
	}
	
	private String innerTab(int level) {
		return TAB.repeat(level + 1);
	}
	
	private String wrapWithDQM(String str) {
		return "\"" + str + "\"";
	}

}
