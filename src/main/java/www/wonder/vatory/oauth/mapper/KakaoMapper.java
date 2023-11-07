package www.wonder.vatory.oauth.mapper;

import org.apache.ibatis.annotations.Mapper;

import www.wonder.vatory.framework.mapper.WonderMapper;
import www.wonder.vatory.oauth.model.KakaoAccountVO;

@Mapper
public interface KakaoMapper extends WonderMapper {
	
	public KakaoAccountVO findByKakaoId(long id);
	
	public int createKakaoAccount(KakaoAccountVO kAccount);
}
