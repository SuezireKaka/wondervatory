package www.wonder.vatory.iis.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import www.wonder.vatory.iis.model.TagRelId;
import www.wonder.vatory.iis.model.TagRelVO;

public interface TagRelRepository extends JpaRepository<TagRelVO, TagRelId>{
	
	@Query(nativeQuery = true, value = "delete from t_tgt_tag where tgt_name = 'T_work' and tgt_id = :postId")
	void deleteAllByPostId(@Param("postId") String postId);
}
