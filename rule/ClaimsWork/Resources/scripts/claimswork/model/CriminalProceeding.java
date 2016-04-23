package claimswork.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import reference.model.OrgCategory;

@Entity
@Table(name = "criminal_proceedings")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "CriminalProceeding.findAll", query = "SELECT m FROM CriminalProceeding AS m ORDER BY m.regDate")
public class CriminalProceeding extends Claim {
	private OrgCategory claimant;

	private String defendantType;
}
