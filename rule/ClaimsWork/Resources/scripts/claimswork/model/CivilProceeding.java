package claimswork.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.exponentus.common.model.Attachment;
import com.exponentus.scripting._Session;
import com.exponentus.util.Util;
import reference.model.DefendantType;

@Entity
@Table(name = "civil_proceedings")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "CivilProceeding.findAll", query = "SELECT m FROM CivilProceeding AS m ORDER BY m.regDate")
public class CivilProceeding extends Claim {

	@ManyToOne
	@JoinColumn
	private DefendantType claimant;

	@ManyToOne
	@JoinColumn
	private DefendantType defendant;

	private Date basisDate;

	private Date dueDate;

	@Column(name = "state_fees")
	private int stateFees;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "state_fees_attachments", joinColumns = { @JoinColumn(name = "parent_id", referencedColumnName = "id") }, inverseJoinColumns = {
	        @JoinColumn(name = "attachment_id", referencedColumnName = "id") })
	private List<Attachment> stateFeesAttachments;

	public DefendantType getClaimant(){return claimant;}

	public void setClaimant(DefendantType claimant) {
		this.claimant = claimant;
	}

	public DefendantType getDefendant(){return defendant;}

	public void setDefendant(DefendantType defendant) {
		this.defendant = defendant;
	}

	public Date getBasisDate(){return basisDate;}

	public void setBasisDate(Date basisDate) {
		this.basisDate = basisDate;
	}

	public Date getDueDate(){return dueDate;}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getStateFees(){return stateFees;}

	public void setStateFees(int stateFees) {
		this.stateFees = stateFees;
	}

	@Override
	public String getShortXMLChunk(_Session ses) {
		StringBuilder chunk = new StringBuilder(1000);
		chunk.append("<regnumber>" + getRegNumber() + "</regnumber>");
		chunk.append("<department>" + getDepartment().getLocalizedName(ses.getLang()) + "</department>");
		chunk.append("<executor>" + getExecutor().getLocalizedName(ses.getLang()) + "</executor>");
		chunk.append("<proceedingtype>" + getProceedingtype() + "</proceedingtype>");
		chunk.append("<basis>" + getBasis() + "</basis>");
		return chunk.toString();
	}


	@Override
	public String getFullXMLChunk(_Session ses) {
		return super.getFullXMLChunk(ses) +
				"<claimant id=\"" + claimant.getId() + "\">" + claimant.getLocalizedName(ses.getLang()) + "</claimant>" +
				"<defendant id=\"" + defendant.getId() + "\">" + defendant.getLocalizedName(ses.getLang()) + "</defendant>"+
				"<basisdate>" + Util.convertDateToStringSilently(basisDate) + "</basisdate>"+
				"<duedate>" + Util.convertDateToStringSilently(dueDate) + "</duedate>"+
				"<statefees>" + stateFees + "</statefees>";
	}
}
