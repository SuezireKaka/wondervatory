package www.wonder.vatory.framework.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import www.wonder.vatory.framework.model.CodeVO;
import www.wonder.vatory.framework.model.RemoconVO;

@Mapper		//Container에 담기도록 지정
public interface CodeMapper {
	//LRCUD 순서로 함수들을 배치하여 빠르게 따라갈(추적성) 수 있도록 합니다. 
	public List<CodeVO> listAll();

	public RemoconVO getRemoconByName(String name);
}
