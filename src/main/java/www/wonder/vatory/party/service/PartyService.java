package www.wonder.vatory.party.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.party.mapper.PartyMapper;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.party.model.ContactPointVO;
import www.wonder.vatory.party.model.OrganizationVO;
import www.wonder.vatory.party.model.PartyVO;
import www.wonder.vatory.party.model.PersonVO;
import www.wonder.vatory.party.model.RoleVO;
import www.wonder.vatory.party.model.SignUpDto;


@Service
public class PartyService implements UserDetailsService {
	@Autowired(required = false)
	private PartyMapper partyMapper;

	@Autowired(required = false)
	private PasswordEncoder pswdEnc;
	
	/** 회사의 모든 계정 조회 */ 
	public DreamPair<List<AccountVO>, PagingDTO> listAllAccount(String ownerId, int page) {
		PagingDTO paging = new PagingDTO(page);
		List<AccountVO> listResult = partyMapper.listAllAccount(ownerId, paging);
		
		for (AccountVO account : listResult) {
			PartyVO response = account.getResponse();
			List<ContactPointVO> contacts = partyMapper.listAllCpOf(response.getId());
			response.setContactPointList(contacts);
		}

		long dataCount = partyMapper.getFoundRows();
		paging.buildPagination(dataCount);

		return new DreamPair<List<AccountVO>, PagingDTO>(listResult, paging);
	}
	
	public AccountVO findByNick(String username) {
		return partyMapper.findByNick(username);
	}
	
	public AccountVO findByLoginId(String loginId) {
		return partyMapper.findByLoginId(loginId);
	}
	//00000000


	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return partyMapper.findByNick(username);
	}

	public int createOrganization(OrganizationVO organization) {
		return partyMapper.createOrganization(organization);
	}

	public int createManager(List<AccountVO> accountList) {
		int res = 1;
		for (AccountVO account : accountList) {
			res = partyMapper.createManager(account);
			if (res == 0) {
				return 0;
			}
		}
		return res;
	}
	
	public boolean checkLoginId(String loginId) {
		return partyMapper.isValidLoginId(loginId);
	}
	public boolean checkNick(String nick) {
		return partyMapper.isValidNick(nick);
	}

	/** 회원 가입 */
	public int createMember(SignUpDto signUpRequest) {
		// 동일인 검증 어케 하나요 ㅠㅠ
		// 모르겠고 빌더패턴이나 쓰자
		PersonVO person = PersonVO.builder()
				.name(signUpRequest.getName())
				.sex(signUpRequest.getSex())
				.birthDate(signUpRequest.getBirthDate())
				.contactPointList(new ArrayList<ContactPointVO>())
				.build();
		AccountVO account = AccountVO.builder()
				.loginId(signUpRequest.getLoginId())
				.passWord(signUpRequest.getPassWord())
				.nick(signUpRequest.getNick())
				.introduction("")
				.owner(new OrganizationVO("0000"))
				.response(person)
				.build();
		for (ContactPointVO cp : signUpRequest.getListContactPoint()) {
			person.addCP(cp);
		}
		int cnt = partyMapper.createPerson(person);
		account.encodePswd(pswdEnc);

		cnt &= partyMapper.createAccount(account)
				& partyMapper.createRole(account, new RoleVO("reader"));
		return cnt;
	}


}
