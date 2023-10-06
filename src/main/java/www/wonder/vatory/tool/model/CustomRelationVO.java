package www.wonder.vatory.tool.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CustomRelationVO extends CustomObjectVO {
	CustomEntityVO one;
	CustomEntityVO other;
}
