package www.wonder.vatory.report.model;

import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.party.model.AccountVO;

@Entity
@Table(name="t_report")
@NoArgsConstructor
@Getter
@Setter
public class ReportDTO {

	@Id
	@GeneratedValue
	private BigInteger ordinal;
	
	private AccountVO reporter;
	


	private String cause;
	
	@Column(name="complete")
	private boolean isComplete;
	
	private AccountVO processer;
	
	private String processMsg;
}