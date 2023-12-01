package www.wonder.vatory.framework.service;

import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.party.model.RoleVO;

public class WonderService {
	public boolean failToAuth(AccountVO account) {
		// 어카운트가 없거나 매니저 또는 어드민이 아니라면 실패
		return account == null || !(account.getRoleList().contains(new RoleVO("Manager"))
				|| !(account.getRoleList().contains(new RoleVO("Admin"))));
	}
}
