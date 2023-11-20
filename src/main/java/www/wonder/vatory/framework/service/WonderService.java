package www.wonder.vatory.framework.service;

import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.party.model.RoleVO;

public class WonderService {
	public boolean failToAuth(AccountVO account) {
		return account == null || !(account.getRoleList().contains(new RoleVO("Manager"))
				|| account.getRoleList().contains(new RoleVO("Admin")));
	}
}
