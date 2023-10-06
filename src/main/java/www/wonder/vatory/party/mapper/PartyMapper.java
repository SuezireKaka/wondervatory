package www.wonder.vatory.party.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.party.model.PersonVO;

@Mapper
public interface PartyMapper {
	public List<PersonVO> listAllMember(String ownerId);
	
	public AccountVO findByNick(String nick);
}
