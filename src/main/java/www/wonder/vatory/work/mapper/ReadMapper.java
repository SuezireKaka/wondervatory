package www.wonder.vatory.work.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import www.wonder.vatory.framework.mapper.WonderMapper;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.work.model.ReadVO;
import www.wonder.vatory.work.model.SemiPostVO;

@Mapper		//Container에 담기도록 지정
public interface ReadMapper extends WonderMapper {
	
	public List<ReadVO> listAllReadBetween(@Param("start") Date start, @Param("end") Date end);
	
	public List<ReadVO> listRead();
	
	public boolean hasReadToday(@Param("reader") AccountVO reader,
			@Param("readee") SemiPostVO readee);
	
	public int read(@Param("reader") AccountVO reader, @Param("readee") SemiPostVO readee);
}
