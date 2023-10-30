package www.wonder.vatory.party.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import www.wonder.vatory.framework.mapper.MetaMapper;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.party.model.ContactPointVO;
import www.wonder.vatory.party.model.OrganizationVO;
import www.wonder.vatory.party.model.PersonVO;
import www.wonder.vatory.party.model.RoleVO;

@Mapper
public interface PartyMapper extends MetaMapper {
	public List<AccountVO> listAllAccount(@Param("ownerId") String ownerId, @Param("paging") PagingDTO paging);
	public List<ContactPointVO> listAllCpOf(@Param("ownerId") String ownerId);
	
	public AccountVO findByLoginId(String loginId);
	public AccountVO findByNick(String nick);
	
	public boolean isValidLoginId(String loginId);
	public boolean isValidNick(String nick);
	
	public int createOrganization(OrganizationVO organization);
	public int createManager(AccountVO account);
	public int createPerson(PersonVO person);
	public int createAccount(AccountVO account);
	public int createRole(@Param("account") AccountVO account, @Param("role") RoleVO role);
	

	public int deleteMember(String nick);
}
