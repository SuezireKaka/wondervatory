package www.wonder.vatory.work.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadTableDTO {
	String name;
	List<ReadCountVO> readCountList;
}
