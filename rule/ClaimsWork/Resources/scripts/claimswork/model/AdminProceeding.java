package claimswork.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import reference.model.OrgCategory;

@Entity
@Table(name = "admin_proceedings")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "AdminProceeding.findAll", query = "SELECT m FROM AdminProceeding AS m ORDER BY m.regDate")
public class AdminProceeding extends Claim {

	private OrgCategory claimant;

	private Date basisDate;

	private Date dueDate;
}
