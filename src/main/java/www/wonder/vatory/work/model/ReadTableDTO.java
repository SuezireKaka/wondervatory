package www.wonder.vatory.work.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Collectors;

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
	
	public void addReadCnt(ReadCountVO readCnt) {
		readCountList.add(readCnt);
	}
	
	public void setReadCnt(Date date, int cnt) {
		for (ReadCountVO readCnt : readCountList) {
			if (date.equals(readCnt.getTime())) {
				
			}
		}
	}
}
