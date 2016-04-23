package claimswork.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "civil_proceedings")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "CivilProceeding.findAll", query = "SELECT m FROM CivilProceeding AS m ORDER BY m.regDate")
public class CivilProceeding extends Claim {

	private String claimant;

	private String defendant;

}
