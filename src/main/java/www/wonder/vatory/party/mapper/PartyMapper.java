package www.wonder.vatory.party.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.party.model.PersonVO;

@Mapper
public interface PartyMapper {
	public long getFoundRows();
	public List<AccountVO> listAllAccount(@Param("ownerId") String ownerId, @Param("paging") PagingDTO paging);
	
	public AccountVO findByNick(String nick);
}
