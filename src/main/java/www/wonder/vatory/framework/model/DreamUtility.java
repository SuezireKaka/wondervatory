package www.wonder.vatory.framework.model;

import java.util.ArrayList;
import java.util.List;

public abstract class DreamUtility<T> {
	public static <T> List<T> upcastList(List<? extends T> extendsList) {
		List<T> resultList = new ArrayList<>();
		resultList.addAll(extendsList);
		return resultList;
	}
}
