package www.wonder.vatory.party.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
import www.wonder.vatory.party.model.WonderAccountVO;


@Service
public class PartyService implements UserDetailsService {
	@Autowired(required = false)
	protected PartyMapper partyMapper;

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
	
	public AccountVO findById(String id) {
		AccountVO res = partyMapper.findById(id);
		PersonVO response = res.getResponse();
		List<ContactPointVO> contacts = partyMapper.listAllCpOf(response.getId());
		response.setContactPointList(contacts);
		res.setResponse(response);
	
		return res;
	}
	
	public AccountVO findByLoginId(String loginId) {
		return partyMapper.findByLoginId(loginId);
	}
	
	public AccountVO findWriterByWorkIdFrom(String id, String table) {
		return partyMapper.findWriterByWorkIdFrom(id, table);
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

	public boolean checkUniqueVal(String key, String val) {
		return partyMapper.checkUniqueVal(key, val);
	}
	
	/** 회원 가입 */
	public int mngMember(SignUpDto signUpRequest) {
		// 동일인 검증 어케 하나요 ㅠㅠ
		// 모르겠고 빌더패턴이나 쓰자
		PersonVO person = PersonVO.builder()
				.name(signUpRequest.getName())
				.sex(signUpRequest.getSex())
				.birthDate(signUpRequest.getBirthDate())
				.build();
		WonderAccountVO account = WonderAccountVO.builder()
				.loginId(signUpRequest.getLoginId())
				.passWord(signUpRequest.getPassWord())
				.nick(signUpRequest.getNick())
				.introduction(signUpRequest.getIntroduction())
				.owner(new OrganizationVO("0000"))
				.response(person)
				.build();
		int cnt = 1;
		account.encodePswd(pswdEnc);
		// accountId가 비어있으면 생성 아니면 수정
		if (ObjectUtils.isEmpty(signUpRequest.getAccountId())) {
			cnt &= partyMapper.createPerson(person);
			cnt &= partyMapper.createAccount(account)
					& partyMapper.createRole(account, new RoleVO("reader"));
			if (signUpRequest.getListContactPoint().size() > 0) {
				cnt &= partyMapper.createAllCpOf(person.getId(), signUpRequest.getListContactPoint());
			}
			return cnt;
		}
		else {
			person.setId(signUpRequest.getPartyId());
			account.setId(signUpRequest.getAccountId());
			cnt &= partyMapper.updatePerson(person)
					& partyMapper.updateAccount(account)
					& partyMapper.deleteAllCpOf(person.getId());
			if (signUpRequest.getListContactPoint().size() > 0) {
				cnt &= partyMapper.createAllCpOf(person.getId(), signUpRequest.getListContactPoint());
			}
			return cnt;
		}
		
	}
	
	public int createPerson(PersonVO person) {
		return partyMapper.createPerson(person);
	}
	
	public int updateStatus(String memberId, String role) {
		return partyMapper.updateStatus(memberId, role);
	}
	
	public int reRole(String memberId, String loginResultCode) {
		return partyMapper.reRole(memberId, loginResultCode);
	}

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		return partyMapper.findById(id);
	}

	public int deleteMember(String id) {
		return partyMapper.deleteMember(id);
	}
	

}
