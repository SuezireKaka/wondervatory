package www.wonder.vatory.oauth.mapper;

import org.apache.ibatis.annotations.Mapper;

import www.wonder.vatory.framework.mapper.MetaMapper;
import www.wonder.vatory.oauth.model.KakaoAccountVO;

@Mapper
public interface KakaoMapper extends MetaMapper {
	
	public KakaoAccountVO findByKakaoId(Long id);
	
	public void createKakaoAccount(KakaoAccountVO kAccount);
}
