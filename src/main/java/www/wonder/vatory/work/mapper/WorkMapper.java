package www.wonder.vatory.work.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.work.model.ReplyVO;

@Mapper
public interface WorkMapper {
	public long getFoundRows();
	public List<ReplyVO> listAllPost(@Param("boardId") String boardId, @Param("paging") PagingDTO paging);
}
