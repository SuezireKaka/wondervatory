package www.wonder.vatory.work.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import www.wonder.vatory.framework.mapper.WonderMapper;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.work.model.PostVO;
import www.wonder.vatory.work.model.ReplyVO;
import www.wonder.vatory.work.model.SemiPostVO;
import www.wonder.vatory.work.model.SeriesVO;

@Mapper
@CrossOrigin(origins = "*", allowedHeaders = "*")
public interface WorkMapper extends WonderMapper {

	public List<SeriesVO> listAllSeries(@Param("boardId") String boardId,
			@Param("paging") PagingDTO paging, @Param("genreId") String genreId);
	public List<PostVO> listAllPost(@Param("seriesId") String seriesId,
			@Param("paging") PagingDTO paging);
	public List<SeriesVO> listUserSeries(@Param("id") String id,
			@Param("paging") PagingDTO paging);

	public List<ReplyVO> searchByTfIdf(@Param("boardId") String boardId,
			@Param("arrSearch") String[] arrSearch, @Param("paging") PagingDTO paging, String descrim);
	
	public List<SemiPostVO> findSeriesById(String id);
	public List<SemiPostVO> findPostById(String id);
	
	public PostVO findPostByEpinum(String seriesId, int offset);
	
	public void incReadCount(String id);
	
	public PostVO getPrev(String parentId, String id);
	public PostVO getNext(String parentId, String id);
	
	public int onLike(String id);
	public int onDisLike(String id);
	
	public int countPostsOf(String seriesId);
	
	public boolean isFirstFavorites(String ownerId, String responseId);
	public boolean isFavorites(String ownerId, String responseId);
	
	public int createSemiPost(@Param("semiPost") SemiPostVO semiPost,
			@Param("type") String type);
	public int updateSemiPost(@Param("semiPost") SemiPostVO semiPost,
			@Param("type") String type);
	public int firstFavorites(String ownerId, String responseId);
	
	public List<SeriesVO> favoritesAll(@Param("ownerId") String ownerId,
			@Param("paging") PagingDTO paging);
	public int toggleFavorites(String ownerId, String responseId);
	
	public int deleteReply(String id);

	
}
