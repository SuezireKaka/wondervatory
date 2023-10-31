package www.wonder.vatory.framework.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RemoconVO {
	private String name;
	private List<RemoteKeyVO> remoteKeyList;
}
