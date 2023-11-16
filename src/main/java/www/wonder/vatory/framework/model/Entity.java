package www.wonder.vatory.framework.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class Entity {
	public static final int UNIT_LENGTH = 4;
	
	//primitive type의 경우 기본값. reference type의 경우 null
	private String id;
	private int hTier;
	
	public void recurToParent() {
		this.id = id.substring(0, id.length() - 4);
		this.retier(0);
	}
	
	public String getParentId() {
		String myId = this.id;
		int len = myId.length();
		return myId.substring(0, len - UNIT_LENGTH);
	}
	
	public void retier(int control) {
		this.hTier = (this.id.length() - control) / 4;
	}
	
	public static String setMultiId(List<String> idList, List<Entity> entityList) {
		int entitySize = entityList.size();
		int idSize = idList.size();
		int minSize = Math.min(entitySize, idSize);
		// 두 리스트 중 작은 수만큼
		for (int i = 0; i < minSize; i++) {
			// entityList의 위치에서 idList를 찾아서 매칭한다
			entityList.get(i).setId(idList.get(i));
		}
		// 디버깅용 리턴
		return (entitySize > idSize
				? "엔티티가 많아서 다 못 바꿨어요......"
				: entitySize < idSize
				? "아이디가 많아서 다 바꾸긴 했는데 남았어요......"
				: "개수가 같아서 완벽하게 처리했어요!"
				) + "\n바꾼 개수 : " + minSize + ", 남은 개수 : " + (idSize - minSize);
	}
	
	@Override
	public String toString() {
		return "id=" + id;
	}
}
