package www.wonder.vatory.work.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.work.model.PostVO;
import www.wonder.vatory.work.model.ReplyVO;
import www.wonder.vatory.work.model.SeriesVO;

@Mapper
public interface WorkMapper {
	public long getFoundRows();

	public List<SeriesVO> listAllSeries(@Param("boardId") String boardId, @Param("paging") PagingDTO paging);
	public List<PostVO> listAllPost(@Param("seriesId") String seriesId, @Param("paging") PagingDTO paging);
	
	public List<ReplyVO> searchByTfIdf(@Param("boardId") String boardId,
			@Param("arrSearch") String[] arrSearch, @Param("paging") PagingDTO paging);
	
	public List<ReplyVO> findById(String id);
	public void incReadCount(String id);
	
	
	public int updatePost(PostVO post);
	public int updateReply(ReplyVO reply);
	
	public int deleteReply(String id);
}
