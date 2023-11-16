package www.wonder.vatory.work.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import www.wonder.vatory.framework.mapper.WonderMapper;
import www.wonder.vatory.work.model.ReadVO;

@Mapper		//Container에 담기도록 지정
public interface ReadMapper extends WonderMapper {
	
	List<ReadVO> listAllReadBetween(Date start, Date end);

}
