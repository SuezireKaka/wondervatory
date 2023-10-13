package www.wonder.vatory.party.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationVO extends PartyVO {
	public OrganizationVO(String id) {
		this.setId(id);
	}
}
